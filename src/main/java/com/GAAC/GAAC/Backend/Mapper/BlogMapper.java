package com.GAAC.GAAC.Backend.Mapper;

import com.GAAC.GAAC.Backend.DTO.response.BlogResponseDTO;
import com.GAAC.GAAC.Backend.Model.Blog;

public class BlogMapper {
    public static BlogResponseDTO toBlogResponse(Blog blog){
        BlogResponseDTO dto = new BlogResponseDTO();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setContent(blog.getContent());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setAuthor(blog.getAuthor());
        return dto;
    }
}
