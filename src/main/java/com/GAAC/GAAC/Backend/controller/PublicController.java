package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.configuration.OtpEncoder;
import com.GAAC.GAAC.Backend.model.dto.request.AuthRequestDTO;
import com.GAAC.GAAC.Backend.model.dto.request.MailDTO;
import com.GAAC.GAAC.Backend.model.dto.request.UserSighInDTO;
import com.GAAC.GAAC.Backend.model.dto.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.InsightResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.service.BlogService;
import com.GAAC.GAAC.Backend.service.EmailService;
import com.GAAC.GAAC.Backend.service.InsightService;
import com.GAAC.GAAC.Backend.service.UserService;
import com.GAAC.GAAC.Backend.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private InsightService insightService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpEncoder optEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Operation(
            summary = "Login",
            description = "Login using the mail and password"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO authRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getToken()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthRequestDTO(userDetails.getUsername(), token));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @Operation(
            summary = "Send otp for signin",
            description = "Step-1: Email is sent to user inbox"
    )
    @GetMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody MailDTO emailDTO){
        try{
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
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "SighIn",
            description = "Step-2: Login using the mail, otp and a new password"
    )
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSighInDTO user){
        try{
            userService.signIn(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Forget password",
            description = "Reset password using otp sent to user email"
    )
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody UserSighInDTO user){
        try{
            userService.forgetPassword(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get all blogs",
            description = "Returns all user blogs"
    )
    @GetMapping("/get-all-blogs")
    public ResponseEntity<?> getAllBlogs(){
        try{
            List<BlogResponseDTO> blogs = blogService.getAllBlogs();
            if(blogs != null && !blogs.isEmpty()){
                return new ResponseEntity<>(blogs, HttpStatus.OK);
            }else throw new RuntimeException("No data available");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get all insights",
            description = "Returns all user insights"
    )
    @GetMapping("/get-all-insights")
    public ResponseEntity<?> getAllInsights(){
        try{
            List<InsightResponseDTO> insights = insightService.getAllInsights();
            if(insights != null && !insights.isEmpty()){
                return new ResponseEntity<>(insights, HttpStatus.OK);
            } else throw new RuntimeException("No data available");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get blogs by team",
            description = "Returns team specific blogs"
    )
    @GetMapping("/get-team-blogs/{teamName}")
    public ResponseEntity<?> getTeamBlogs(@PathVariable TeamEnum teamName){
        try{
            List<BlogResponseDTO> teamBlogs = blogService.getTeamBlogs(teamName);
            return new ResponseEntity<>(teamBlogs,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get members by team",
            description = "Returns members by their team"
    )
    @GetMapping("/get-team-members/{teamName}")
    public ResponseEntity<?> getTeamMembers(@PathVariable TeamEnum teamName){
        try{
            List<UserMiniResponseDTO> teamMembers = userService.getTeamMembers(teamName);
            return new ResponseEntity<>(teamMembers,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Testing",
            description = "Returns positive is connection is secured"
    )
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Positive";
    }

}
