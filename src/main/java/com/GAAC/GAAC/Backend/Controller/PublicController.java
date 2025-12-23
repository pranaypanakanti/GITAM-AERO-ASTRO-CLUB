package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.Configuration.OtpEncoder;
import com.GAAC.GAAC.Backend.DTO.request.MailDTO;
import com.GAAC.GAAC.Backend.DTO.request.UserDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.request.UserSighInDTO;
import com.GAAC.GAAC.Backend.DTO.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.DTO.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
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

    @Autowired
    private OtpEncoder optEncoder;

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserSighInDTO user){
        try{
            userService.login(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody MailDTO emailDTO){
        String userEmail = emailDTO.getEmail(); // The recipient
        String otp = optEncoder.otpEncoder(userEmail);// Your generated OTP

        String subject = "Verify Your Account - GITAM Aero Astro Club \uD83D\uDE80";

        String body = "Welcome to the Skies!\n" +
                "Hello,\n\n" +
                "Thank you for joining the GITAM Aero Astro Club! We are excited to have you on board.\n\n" +
                "To complete your registration, please use the following One-Time Password (OTP):\n\n" +
                "OTP: " + otp + "\n\n" +
                "This code is valid for the next 10 minutes. Please do not share this code with anyone.\n\n" +
                "Team GAAC\n";

        emailService.sendEmail(userEmail, subject, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSighInDTO user){
        try{
            userService.signIn(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody UserSighInDTO user){
        try{
            userService.forgetPassword(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
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

    @GetMapping("/get-team-members/{teamName}")
    public ResponseEntity<?> getTeamMembers(@PathVariable TeamEnum teamName){
        List<UserMiniResponseDTO> teamMembers = userService.getTeamMembers(teamName);
        return new ResponseEntity<>(teamMembers,HttpStatus.OK);
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Positive";
    }

}
