package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.Achievement;
import com.GAAC.GAAC.Backend.model.Blog;
import com.GAAC.GAAC.Backend.model.Project;
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

    @Operation(summary = "Get all achievements or search by title/description")
    @GetMapping("/get-all-achievements")
    public ResponseEntity<?> getAchievements(
            @RequestParam(required = false) String search) {

        try {
            List<Achievement> achievements = achievementService.searchAchievements(search);

            if (achievements.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No achievements found");
            }

            return ResponseEntity.ok(achievements);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch achievements: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all projects or search by title/description")
    @GetMapping("/get-all-projects")
    public ResponseEntity<?> getProjects(
            @RequestParam(required = false) String search) {

        try {
            List<Project> projects = projectService.searchProjects(search);

            if (projects.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No projects found");
            }

            return ResponseEntity.ok(projects);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch projects: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all blogs or search by title/description")
    @GetMapping("/get-all-blogs")
    public ResponseEntity<?> getBlogs(
            @RequestParam(required = false) String search) {

        try {
            List<Blog> blogs = blogService.searchBlogs(search);

            if (blogs.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No blogs found");
            }

            return ResponseEntity.ok(blogs);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch blogs: " + e.getMessage());
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
