package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.configuration.OtpEncoder;
import com.GAAC.GAAC.Backend.model.dto.request.MailDTO;
import com.GAAC.GAAC.Backend.model.dto.request.UserSighInDTO;
import com.GAAC.GAAC.Backend.model.dto.response.AuthResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.InsightResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.service.*;
import com.GAAC.GAAC.Backend.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Send OTP for sign-in",
            description = "Step-1: Email is sent to user inbox"
    )
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody MailDTO emailDTO) {
        try {
            authService.sendOtp(emailDTO.getEmail());
            return ResponseEntity.ok().body("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Sign-up with OTP",
            description = "Step-2: Creates account with OTP verification and returns JWT tokens"
    )
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSighInDTO user) {
        try {
            AuthResponseDTO authResponse = authService.signUpWithOtp(user);

            ResponseCookie refreshTokenCookie = authService.createRefreshTokenCookie(
                    authResponse.getRefreshToken()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(authResponse);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(
            summary = "Forget password",
            description = "Reset password using OTP"
    )
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody UserSighInDTO user) {
        try {
            authService.resetPasswordWithOtp(user);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
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
