package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Positive";
    }

    @PostMapping("/new-user")
    public ResponseEntity<User> createNewUser(@RequestBody User myEntry){
        try{
            userService.saveNewUser(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST);
        }
    }
}
