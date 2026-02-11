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
    private AuthService authService;

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
