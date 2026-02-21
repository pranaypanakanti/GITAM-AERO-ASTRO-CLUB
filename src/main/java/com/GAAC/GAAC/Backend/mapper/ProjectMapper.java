package com.GAAC.GAAC.Backend.mapper;

import com.GAAC.GAAC.Backend.model.Project;
import com.GAAC.GAAC.Backend.model.dto.response.ProjectResponseDTO;

public class ProjectMapper {
    public static ProjectResponseDTO toProjectResponse(Project project){
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setContent(project.getContent());
        dto.setTeam(project.getTeam());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setPriority(project.getPriority());
        return dto;
    }
}
