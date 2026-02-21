package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.dto.response.AchievementResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.ProjectResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.service.*;
import io.swagger.v3.oas.annotations.Operation;
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
    private ProjectService projectService;

    @Autowired
    private AchievementService achievementService;

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
            summary = "Get all achievements",
            description = "Returns all user achievements"
    )
    @GetMapping("/get-all-achievements")
    public ResponseEntity<?> findAllAchievementsByPriority(){
        try{
            List<AchievementResponseDTO> achievements = achievementService.findAllAchievementsByPriority();
            if(achievements != null && !achievements.isEmpty()){
                return new ResponseEntity<>(achievements, HttpStatus.OK);
            }else throw new RuntimeException("No data available");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get all projects",
            description = "Returns all user projects"
    )
    @GetMapping("/get-all-projects")
    public ResponseEntity<?> findAllProjectsByPriority(){
        try{
            List<ProjectResponseDTO> projects = projectService.findAllProjectsByPriority();
            if(projects != null && !projects.isEmpty()){
                return new ResponseEntity<>(projects, HttpStatus.OK);
            }else throw new RuntimeException("No data available");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Operation(
            summary = "Get achievements by team",
            description = "Returns team specific achievements"
    )
    @GetMapping("/get-team-achievements/{teamName}")
    public ResponseEntity<?> getTeamAchievements(@PathVariable TeamEnum teamName){
        try{
            List<AchievementResponseDTO> teamAchievements = achievementService.getTeamAchievements(teamName);
            return new ResponseEntity<>(teamAchievements,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get projects by team",
            description = "Returns team specific projects"
    )
    @GetMapping("/get-team-projects/{teamName}")
    public ResponseEntity<?> getTeamProjects(@PathVariable TeamEnum teamName){
        try{
            List<ProjectResponseDTO> teamProjects = projectService.getTeamProjects(teamName);
            return new ResponseEntity<>(teamProjects,HttpStatus.OK);
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
