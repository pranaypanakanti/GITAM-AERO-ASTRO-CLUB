package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.Model.User;
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

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAll();
        if(users.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/make-admin/{id}")
    public ResponseEntity<?> makeAsAdmin(@RequestBody User user, @PathVariable UUID id){
        userService.makeAsAdmin(user,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/make-member/{id}")
    public ResponseEntity<?> makeAsMember(@RequestBody User user, @PathVariable UUID id){
        userService.makeAsMember(user,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/make-user/{id}")
    public ResponseEntity<?> makeAsUser(@RequestBody User user, @PathVariable UUID id){
        userService.makeAsUser(user,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
