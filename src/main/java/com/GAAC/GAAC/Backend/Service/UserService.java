package com.GAAC.GAAC.Backend.Service;

import com.GAAC.GAAC.Backend.DTO.request.UserDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.DTO.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.ENUMS.RoleEnum;
import com.GAAC.GAAC.Backend.Mapper.UserMapper;
import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;


    public void saveNewUser(UserDetailsDTO user){
        User newUser = new User();
        newUser.setRole(RoleEnum.USER);
        newUser.setName(user.getName());
        newUser.setCollegeId(user.getCollegeId());
        newUser.setBranch(user.getBranch());
        newUser.setMobileNumber(user.getMobileNumber());
        newUser.setYearOfStudy(user.getYearOfStudy());
        newUser.setAASID(user.getAASID());
        newUser.setLinkedinUrl(user.getLinkedinUrl());
        newUser.setImageUrl(user.getImageURL());
        newUser.setTeam(user.getTeam());
        newUser.setPosition(user.getPosition());
        userRepo.save(newUser);
    }

    public boolean updateUser(UserDetailsDTO newUser, String email){
        User oldUser = userRepo.findByEmail(email).orElse(null);
        if(oldUser == null) return false;
        oldUser.setName(newUser.getName() != null && !newUser.getName().isEmpty() ? newUser.getName() : oldUser.getName());
        oldUser.setCollegeId(newUser.getCollegeId() != null && !newUser.getCollegeId().isEmpty() ? newUser.getCollegeId() : oldUser.getCollegeId());
        oldUser.setBranch(newUser.getBranch() != null && !newUser.getBranch().isEmpty() ? newUser.getBranch() : oldUser.getBranch());
        oldUser.setMobileNumber(newUser.getMobileNumber() != null && !newUser.getMobileNumber().isEmpty() ? newUser.getMobileNumber() : oldUser.getMobileNumber());
        oldUser.setYearOfStudy(newUser.getYearOfStudy() != null && !newUser.getYearOfStudy().isEmpty() ? newUser.getYearOfStudy() : oldUser.getYearOfStudy());
        oldUser.setAASID(newUser.getAASID() != null && !newUser.getAASID().isEmpty() ? newUser.getAASID() : oldUser.getAASID());
        oldUser.setLinkedinUrl(newUser.getLinkedinUrl() != null && !newUser.getLinkedinUrl().isEmpty() ? newUser.getLinkedinUrl() : oldUser.getLinkedinUrl());
        oldUser.setImageUrl(newUser.getImageURL() != null && !newUser.getImageURL().isEmpty() ? newUser.getImageURL() : oldUser.getImageUrl());
        oldUser.setTeam(newUser.getTeam() != null ? newUser.getTeam() : oldUser.getTeam());
        oldUser.setPosition(newUser.getPosition() != null? newUser.getPosition() : oldUser.getPosition());
        userRepo.save(oldUser);
        return true;
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public void makeAsAdmin(UUID id) {
        User oldUser = userRepo.findById(id).orElse(null);
        if(oldUser != null) {
            oldUser.setRole(RoleEnum.ADMIN);
            userRepo.save(oldUser);
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public void makeAsMember(UUID id) {
        User oldUser = userRepo.findById(id).orElse(null);
        if(oldUser != null) {
            oldUser.setRole(RoleEnum.MEMBER);
            userRepo.save(oldUser);
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public void makeAsUser(UUID id) {
        User oldUser = userRepo.findById(id).orElse(null);
        if(oldUser != null) {
            oldUser.setRole(RoleEnum.USER);
            userRepo.save(oldUser);
        }else{
            throw new RuntimeException("User not found");
        }
    }

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

    public void deleteUserByEmail(String email){
        User user = userRepo.findByEmail(email).orElse(null);
        if(user == null) throw new RuntimeException("User not found");
        userRepo.deleteByEmail(email);
    }
}
