package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.DTO.request.UserDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.Service.BlogService;
import com.GAAC.GAAC.Backend.Service.EmailService;
import com.GAAC.GAAC.Backend.Service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserDetailsDTO> createNewUser(@Valid @RequestBody UserDetailsDTO myEntry){
        try{
            userService.saveNewUser(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-blogs")
    public ResponseEntity<?> getAllBlogs(){
        List<BlogResponseDTO> blogs = blogService.getAllBlogs();
        if(blogs != null && !blogs.isEmpty()){
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
