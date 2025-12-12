package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id){
        User user = userService.getUserById(id);
        if(user != null) return new ResponseEntity<>(user,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.updateUser(newUser,email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
