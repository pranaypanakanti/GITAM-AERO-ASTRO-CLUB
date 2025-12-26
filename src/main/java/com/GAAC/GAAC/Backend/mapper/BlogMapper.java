package com.GAAC.GAAC.Backend.mapper;

import com.GAAC.GAAC.Backend.model.dto.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.model.Blog;

public class BlogMapper {
    public static BlogResponseDTO toBlogResponse(Blog blog){
        BlogResponseDTO dto = new BlogResponseDTO();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setContent(blog.getContent());
        dto.setTeam(blog.getTeam());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setAuthorName(
                blog.getAuthor() != null ? blog.getAuthor().getName() : "Unknown"
        );
        return dto;
    }
}
