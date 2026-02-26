package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.Project;
import com.GAAC.GAAC.Backend.model.dto.request.ProjectDetailsDTO;
import com.GAAC.GAAC.Backend.service.ProjectService;
import com.GAAC.GAAC.Backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/project")
public class ProjectsController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Post project",
            description = "Create a new project"
    )
    @PostMapping("/new-project")
    public ResponseEntity<ProjectDetailsDTO> createProject(@Valid @RequestBody ProjectDetailsDTO myProject){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            projectService.saveProject(myProject);
            return new ResponseEntity<>(myProject,HttpStatus.CREATED);
        }catch (RuntimeException e){
            throw  new RuntimeException("Project already exists");
        }catch (Exception e){
            return new ResponseEntity<>(myProject,HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(
            summary = "Delete project",
            description = "Deletes project by id"
    )
    @DeleteMapping("/delete-project/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable UUID projectId){
        try {
            Project project = projectService.getProjectById(projectId).orElse(null);
            if(project == null) throw new RuntimeException("Project not found");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            projectService.deleteProjectById(projectId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Update project",
            description = "Update project by id"
    )
    @PutMapping("/update-project/{projectId}")
    public ResponseEntity<?> updateProjectById(@PathVariable UUID projectId,
                                                    @Valid @RequestBody ProjectDetailsDTO newProject){
            try {
                projectService.updateProjectById(projectId,newProject);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
    }
}
