package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.dto.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.service.EmailService;
import com.GAAC.GAAC.Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<UserMiniResponseDTO> users = userService.getAllUsers();
            if(users.isEmpty()) throw new RuntimeException("Users not found");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/get-role-members/{roleName}")
    public ResponseEntity<?> getRoleMembers(@Valid @PathVariable RoleEnum roleName){
        try {
            List<UserMiniResponseDTO> roleMembers = userService.getRoleMembers(roleName);
            return new ResponseEntity<>(roleMembers,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/change-role/{role}/{userId}")
    public ResponseEntity<?> changeRole(@Valid @PathVariable RoleEnum role, @PathVariable UUID userId){
        try {
            userService.changeRole(role,userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/delete-profile-by-id/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId){
        try{
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/send-mail")
    public void sendMail(){
        emailService.sendEmail("garagadharma24@gmail.com","Gentle Remainder from Pranay","Late ayindhi padukora pu..");
    }
}
