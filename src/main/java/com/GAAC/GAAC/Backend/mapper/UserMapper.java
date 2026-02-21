package com.GAAC.GAAC.Backend.mapper;

import com.GAAC.GAAC.Backend.model.dto.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.UserFilterResponseDTO;
import com.GAAC.GAAC.Backend.model.dto.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.model.User;

import java.util.List;

public class UserMapper {

    public static ProfileResponseDTO toProfileResponse(User user) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCollegeId(user.getCollegeId());
        dto.setBranch(user.getBranch());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setYearOfStudy(user.getYearOfStudy());
        dto.setAASID(user.getAASID());
        dto.setLinkedinUrl(user.getLinkedinUrl());
        dto.setRole(user.getRole());
        dto.setTeam(user.getTeam());
        dto.setPosition(user.getPosition());
        dto.setImageUrl(user.getImageUrl());
        List<BlogResponseDTO> blogs = user.getBlogsList().stream().map(blog -> {
            BlogResponseDTO b = new BlogResponseDTO();
            b.setId(blog.getId());
            b.setTitle(blog.getTitle());
            b.setContent(blog.getContent());
            b.setCreatedAt(blog.getCreatedAt());
            return b;
        }).toList();
        return dto;
    }

    public static UserMiniResponseDTO toUserMiniResponse(User user) {
        UserMiniResponseDTO dto = new UserMiniResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCollegeId(user.getCollegeId());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setRole(user.getRole());
        dto.setBranch(user.getBranch());
        dto.setDescription(user.getDescription());
        dto.setYearOfStudy(user.getYearOfStudy());
        dto.setAASID(user.getAASID());
        dto.setLinkedinUrl(user.getLinkedinUrl());
        dto.setTeam(user.getTeam());
        dto.setPosition(user.getPosition());
        dto.setImageUrl(user.getImageUrl());
        return dto;
    }

    public static UserFilterResponseDTO toUserFilterResponse(User user) {
        if (user == null) return null;

        return UserFilterResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .collegeId(user.getCollegeId())
                .yearOfStudy(user.getYearOfStudy())
                .recruitmentStatus(user.getRecruitmentStatus())
                .branch(user.getBranch() != null ? user.getBranch(): null)
                .mobileNumber(user.getMobileNumber() != null ? user.getMobileNumber() : null)
                .team(user.getTeam() != null ? user.getTeam().name() : null)
                .linkedinUrl(user.getLinkedinUrl() != null ? user.getLinkedinUrl() : null)
                .description(user.getDescription() != null ? user.getDescription() : null)
                .build();
    }

}
