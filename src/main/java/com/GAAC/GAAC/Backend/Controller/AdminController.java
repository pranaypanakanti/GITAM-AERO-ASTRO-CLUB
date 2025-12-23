package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.DTO.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.ENUMS.RoleEnum;
import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Service.EmailService;
import com.GAAC.GAAC.Backend.Service.UserService;
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
        List<UserMiniResponseDTO> users = userService.getAllUsers();
        if(users.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/change-role/{role}/{userId}")
    public ResponseEntity<?> changeRole(@PathVariable RoleEnum role, @PathVariable UUID userId){
        userService.changeRole(role,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/send-mail")
    public void sendMail(){
        emailService.sendEmail("garagadharma24@gmail.com","Gentle Remainder from Pranay","Late ayindhi padukora pu..");
    }
}
