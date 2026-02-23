package com.GAAC.GAAC.Backend.service;


import com.GAAC.GAAC.Backend.mapper.ProjectMapper;
import com.GAAC.GAAC.Backend.model.Project;
import com.GAAC.GAAC.Backend.model.User;
import com.GAAC.GAAC.Backend.model.dto.request.ProjectDetailsDTO;
import com.GAAC.GAAC.Backend.model.dto.response.ProjectResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.repository.ProjectRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    public void saveProject(ProjectDetailsDTO project) {
        Project duplicate = projectRepo.findByTitle(project.getTitle()).orElse(null);
        if(duplicate != null && duplicate.getContent().equals(project.getContent())) throw new RuntimeException("Project already exists");
        Project newProject = new Project();
        newProject.setTitle(project.getTitle());
        newProject.setContent(project.getContent());
        newProject.setTeam(project.getTeam());
        projectRepo.save(newProject);
    }

    public List<ProjectResponseDTO> findAllProjectsByPriority() {
        return projectRepo.findAllOrderedByPriority()
                .stream()
                .map(ProjectMapper::toProjectResponse)
                .toList();
    }

    public List<ProjectResponseDTO> getTeamProjects(TeamEnum teamName) {
        return projectRepo.findByTeamOrderByPriority(teamName)
                .stream()
                .map(ProjectMapper::toProjectResponse)
                .toList();
    }

    public Optional<Project> getProjectById(UUID id) {
        return projectRepo.findById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteProjectById(UUID id) {
        projectRepo.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void updateProjectById(UUID projectId, @Valid ProjectDetailsDTO newProject) {
        try{
            Project old = projectRepo.findById(projectId).orElse(null);
            if(old == null) throw new RuntimeException("User not found");
            old.setTitle(newProject.getTitle() != null && !newProject.getTitle().isEmpty() ? newProject.getTitle() : old.getTitle());
            old.setContent(newProject.getContent() != null && !newProject.getContent().isEmpty() ? newProject.getContent() : old.getContent());
            old.setTeam(newProject.getTeam() != null ? newProject.getTeam() : old.getTeam());
            projectRepo.save(old);
        }catch (Exception e) {
            throw new RuntimeException("Failed to update project", e);
        }
    }

    public List<Project> searchProjects(String query) {
        if (query == null || query.trim().isEmpty()) {
            return projectRepo.findAll();
        }
        return projectRepo.searchProjects(query);
    }
}
