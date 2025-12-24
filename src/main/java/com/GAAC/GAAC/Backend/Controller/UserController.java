package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.DTO.request.UserDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.request.UserSighInDTO;
import com.GAAC.GAAC.Backend.DTO.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UserSighInDTO user){
        try{
            userService.changePassword(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/new-profile")
    public ResponseEntity<UserDetailsDTO> createNewUser(@Valid @RequestBody UserDetailsDTO user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try{
            userService.saveNewUser(email,user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserById(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            ProfileResponseDTO user = userService.getUserDTOByEmail(email);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDetailsDTO newUser){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            userService.updateUser(newUser,email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/delete-profile")
    public ResponseEntity<?> deleteUser(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            userService.deleteUserByEmail(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
