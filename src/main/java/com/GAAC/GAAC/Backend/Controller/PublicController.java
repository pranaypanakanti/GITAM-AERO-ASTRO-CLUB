package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.Model.Blog;
import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.Service.BlogService;
import com.GAAC.GAAC.Backend.Service.EmailService;
import com.GAAC.GAAC.Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    public void sendMail(){
        emailService.sendEmail("garagadharma24@gmail.com","Gentle Remainder from Pranay","Late ayindhi padukora pu..");
    }

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

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllBlogs(){
        List<Blog> blog = blogService.getAllBlogs();
        if(blog != null && !blog.isEmpty()){
            return new ResponseEntity<>(blog, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
