package com.GAAC.GAAC.Backend.Service;

import com.GAAC.GAAC.Backend.Configuration.OtpEncoder;
import com.GAAC.GAAC.Backend.DTO.request.UserDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.request.UserSighInDTO;
import com.GAAC.GAAC.Backend.DTO.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.DTO.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.ENUMS.RoleEnum;
import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
import com.GAAC.GAAC.Backend.Exception.InvalidOtpException;
import com.GAAC.GAAC.Backend.Mapper.UserMapper;
import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private OtpEncoder optEncoder;

    public void login(UserSighInDTO user){
        String password = encoder.encode(user.getPassword());
        User OldUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if(OldUser == null) throw new RuntimeException("User not found");
        if(!password.equals(OldUser.getPassword())) throw new RuntimeException("Incorrect password");
    }

    public void signIn(UserSighInDTO user) {
        String givenOtp = user.getOtp();
        String correctOtp = optEncoder.otpEncoder(user.getEmail());
        if(!givenOtp.equals(correctOtp)) {
            throw new InvalidOtpException("The OTP you entered is incorrect. Please try again.");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(RoleEnum.USER);
        userRepo.save(newUser);
    }

    public void forgetPassword(UserSighInDTO user){
        String givenOtp = user.getOtp();
        String correctOtp = optEncoder.otpEncoder(user.getEmail());
        if(!givenOtp.equals(correctOtp)) {
            throw new InvalidOtpException("The OTP you entered is incorrect. Please try again.");
        }
        User oldUser = getUserByEmail(user.getEmail());
        oldUser.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(oldUser);
    }

    public void changePassword(UserSighInDTO user){
        User oldUser = getUserByEmail(user.getEmail());
        oldUser.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(oldUser);
    }

    public void saveNewUser(String email,UserDetailsDTO user){
        User newUser = userRepo.findByEmail(email).orElse(null);
        if(newUser == null) throw new RuntimeException("User not found");
        newUser.setName(user.getName());
        newUser.setCollegeId(user.getCollegeId());
        newUser.setBranch(user.getBranch());
        newUser.setMobileNumber(user.getMobileNumber());
        newUser.setYearOfStudy(user.getYearOfStudy());
        newUser.setAASID(user.getAASID());
        newUser.setDescription(user.getDescription());
        newUser.setLinkedinUrl(user.getLinkedinUrl());
        newUser.setImageUrl(user.getImageURL());
        newUser.setTeam(user.getTeam());
        newUser.setPosition(user.getPosition());
        userRepo.save(newUser);
    }

    public void updateUser(UserDetailsDTO newUser, String email){
        User oldUser = userRepo.findByEmail(email).orElse(null);
        if(oldUser == null) throw new RuntimeException("User not found");
        oldUser.setName(newUser.getName() != null && !newUser.getName().isEmpty() ? newUser.getName() : oldUser.getName());
        oldUser.setCollegeId(newUser.getCollegeId() != null && !newUser.getCollegeId().isEmpty() ? newUser.getCollegeId() : oldUser.getCollegeId());
        oldUser.setBranch(newUser.getBranch() != null && !newUser.getBranch().isEmpty() ? newUser.getBranch() : oldUser.getBranch());
        oldUser.setMobileNumber(newUser.getMobileNumber() != null && !newUser.getMobileNumber().isEmpty() ? newUser.getMobileNumber() : oldUser.getMobileNumber());
        oldUser.setYearOfStudy(newUser.getYearOfStudy() != null && !newUser.getYearOfStudy().isEmpty() ? newUser.getYearOfStudy() : oldUser.getYearOfStudy());
        oldUser.setDescription(newUser.getDescription() != null && !newUser.getDescription().isEmpty() ? newUser.getDescription() : oldUser.getDescription());
        oldUser.setAASID(newUser.getAASID() != null && !newUser.getAASID().isEmpty() ? newUser.getAASID() : oldUser.getAASID());
        oldUser.setLinkedinUrl(newUser.getLinkedinUrl() != null && !newUser.getLinkedinUrl().isEmpty() ? newUser.getLinkedinUrl() : oldUser.getLinkedinUrl());
        oldUser.setImageUrl(newUser.getImageURL() != null && !newUser.getImageURL().isEmpty() ? newUser.getImageURL() : oldUser.getImageUrl());
        oldUser.setTeam(newUser.getTeam() != null ? newUser.getTeam() : oldUser.getTeam());
        oldUser.setPosition(newUser.getPosition() != null? newUser.getPosition() : oldUser.getPosition());
        userRepo.save(oldUser);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void changeRole(RoleEnum role, UUID id) {
        User oldUser = userRepo.findById(id).orElse(null);
        if(oldUser == null) throw new RuntimeException("User not found");
        if(role.equals(oldUser.getRole())) throw new RuntimeException("Already a "+ role.toString());
        if(oldUser.getRole().equals(RoleEnum.ADMIN)) {
            List<User> users = userRepo.findByRole(role);
            if(users.size() <= 1) throw new RuntimeException("Minimum one Admin is required.");
        }
        oldUser.setRole(role);
        userRepo.save(oldUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserMiniResponseDTO> getAllUsers(){
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toUserMiniResponse)
                .toList();
    }

    public ProfileResponseDTO getUserDTOByEmail(String email){
        User user = userRepo.findByEmail(email).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        return UserMapper.toProfileResponse(user);
    }

    public User getUserByEmail(String email){
        User user = userRepo.findByEmail(email).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        return user;
    }


    public List<UserMiniResponseDTO> getTeamMembers(TeamEnum teamName) {
        return userRepo.findByTeam(teamName)
                .stream()
                .map(UserMapper::toUserMiniResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserMiniResponseDTO> getRoleMembers(RoleEnum roleName) {
        return userRepo.findByRole(roleName)
                .stream()
                .map(UserMapper::toUserMiniResponse)
                .toList();
    }

    public void deleteUserByEmail(String email){
        User user = userRepo.findByEmail(email).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        userRepo.deleteByEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserById(UUID userId) {
        User user = userRepo.findById(userId).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        userRepo.deleteById(userId);
    }
}
