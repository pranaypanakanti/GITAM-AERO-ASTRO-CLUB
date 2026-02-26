package com.GAAC.GAAC.Backend.service;

import com.GAAC.GAAC.Backend.model.dto.request.RecruitmentDTO;
import com.GAAC.GAAC.Backend.model.dto.request.UserDetailsDTO;
import com.GAAC.GAAC.Backend.model.dto.request.UserSearchCriteriaDTO;
import com.GAAC.GAAC.Backend.model.dto.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.RecruitmentStatusEnum;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.mapper.UserMapper;
import com.GAAC.GAAC.Backend.model.User;
import com.GAAC.GAAC.Backend.repository.UserRepo;
import com.GAAC.GAAC.Backend.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

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
        newUser.setRecruitmentStatus(RecruitmentStatusEnum.NOT_APPLIED);
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

    public void recruitUser(RecruitmentDTO newUser, String email){
        User oldUser = userRepo.findByEmail(email).orElse(null);
        if(oldUser == null) throw new RuntimeException("User not found");
        oldUser.setName(newUser.getName());
        oldUser.setCollegeId(newUser.getCollegeId());
        oldUser.setBranch(newUser.getBranch());
        oldUser.setMobileNumber(newUser.getMobileNumber());
        oldUser.setYearOfStudy(newUser.getYearOfStudy());
        oldUser.setTeam(newUser.getTeam());
        oldUser.setRecruitmentStatus(RecruitmentStatusEnum.APPLIED);
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
        if(oldUser.getRole().equals(RoleEnum.USER)){
            oldUser.setRecruitmentStatus(null);
        }
        if(role.equals(RoleEnum.USER)){
            oldUser.setRecruitmentStatus(RecruitmentStatusEnum.NOT_APPLIED);
        }
        oldUser.setRole(role);
        userRepo.save(oldUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void changeRecruitmentStatus(RecruitmentStatusEnum status, UUID id) {
        User oldUser = userRepo.findById(id).orElse(null);
        if(oldUser == null) throw new RuntimeException("User not found");
        oldUser.setRecruitmentStatus(status);
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

    public void resetRecruitmentDetails() {
        List<User> list = userRepo.findByRole(RoleEnum.USER);
        if(list != null){
            for(User user: list){
                user.setRecruitmentStatus(RecruitmentStatusEnum.NOT_APPLIED);
            }
        }
    }

    public List<UserMiniResponseDTO> searchUsers(UserSearchCriteriaDTO criteria) {
        Specification<User> spec = UserSpecification.filterAndSortUsers(criteria);

        List<User> users = userRepo.findAll(spec);

        return users.stream()
                .map(UserMapper::toUserMiniResponse)
                .toList();
    }
}
