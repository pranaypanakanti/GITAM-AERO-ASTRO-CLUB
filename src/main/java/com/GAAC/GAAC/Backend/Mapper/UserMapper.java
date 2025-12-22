package com.GAAC.GAAC.Backend.Mapper;

import com.GAAC.GAAC.Backend.Model.User;
import com.GAAC.GAAC.Backend.DTO.response.*;

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
        dto.setRole(user.getRole().name());
        dto.setTeam(user.getTeam());
        dto.setPosition(user.getPosition().name());
        dto.setImageUrl(user.getImageUrl());
        List<BlogResponseDTO> blogs = user.getBlogsList().stream().map(blog -> {
            BlogResponseDTO b = new BlogResponseDTO();
            b.setId(blog.getId());
            b.setTitle(blog.getTitle());
            b.setContent(blog.getContent());
            b.setCreatedAt(blog.getCreatedAt());
            return b;
        }).toList();
        dto.setBlogsList(blogs);
        return dto;
    }

    public static UserMiniResponseDTO toUserMiniResponse(User user) {
        UserMiniResponseDTO dto = new UserMiniResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBranch(user.getBranch());
        dto.setYearOfStudy(user.getYearOfStudy());
        dto.setAASID(user.getAASID());
        dto.setLinkedinUrl(user.getLinkedinUrl());
        dto.setTeam(user.getTeam());
        dto.setPosition(user.getPosition().name());
        dto.setImageUrl(user.getImageUrl());
        return dto;
    }
}
