package com.GAAC.GAAC.Backend.Service;

import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepo userRepo;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();


    public void saveNewUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepo.save(user);
    }

    public void updateUser(User newUser, String email){
        User oldUser = userRepo.findByEmail(email);
        oldUser.setEmail(newUser.getEmail() != null && !newUser.getEmail().isEmpty() ? newUser.getEmail() : oldUser.getEmail());
        oldUser.setName(newUser.getName() != null && !newUser.getName().isEmpty() ? newUser.getName() : oldUser.getName());
        oldUser.setCollegeId(newUser.getCollegeId() != null && !newUser.getCollegeId().isEmpty() ? newUser.getCollegeId() : oldUser.getCollegeId());
        oldUser.setBranch(newUser.getBranch() != null && !newUser.getBranch().isEmpty() ? newUser.getBranch() : oldUser.getBranch());
        oldUser.setMobileNumber(newUser.getMobileNumber() != null && !newUser.getMobileNumber().isEmpty() ? newUser.getMobileNumber() : oldUser.getMobileNumber());
        oldUser.setYearOfStudy(newUser.getYearOfStudy() != null && !newUser.getYearOfStudy().isEmpty() ? newUser.getYearOfStudy() : oldUser.getYearOfStudy());
        oldUser.setAASID(newUser.getAASID() != null && !newUser.getAASID().isEmpty() ? newUser.getAASID() : oldUser.getAASID());
        oldUser.setGithubUrl(newUser.getGithubUrl() != null && !newUser.getGithubUrl().isEmpty() ? newUser.getGithubUrl() : oldUser.getGithubUrl());
        oldUser.setLinkedinUrl(newUser.getLinkedinUrl() != null && !newUser.getLinkedinUrl().isEmpty() ? newUser.getLinkedinUrl() : oldUser.getLinkedinUrl());
        oldUser.setImageURL(newUser.getImageURL() != null && !newUser.getImageURL().isEmpty() ? newUser.getImageURL() : oldUser.getImageURL());
        oldUser.setTeam(newUser.getTeam() != null && !newUser.getTeam().isEmpty() ? newUser.getTeam() : oldUser.getTeam());
        if(newUser.getPassword() != null && !newUser.getPassword().isEmpty()){
            oldUser.setPassword(encoder.encode(newUser.getPassword()));
        }
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public void makeAsAdmin(User user, UUID id) {
        User oldUser = userRepo.getReferenceById(id);
        oldUser.setRole("ADMIN");
        userRepo.save(oldUser);
    }

    public void makeAsMember(User user, UUID id) {
        User oldUser = userRepo.getReferenceById(id);
        oldUser.setRole("MEMBER");
        userRepo.save(oldUser);
    }

    public void makeAsUser(User user, UUID id) {
        User oldUser = userRepo.getReferenceById(id);
        oldUser.setRole("USER");
        userRepo.save(oldUser);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User getUserById(UUID id){
        return userRepo.getReferenceById(id);
    }

    public void deleteUserByEmail(String email){
        userRepo.deleteByEmail(email);
    }
}
