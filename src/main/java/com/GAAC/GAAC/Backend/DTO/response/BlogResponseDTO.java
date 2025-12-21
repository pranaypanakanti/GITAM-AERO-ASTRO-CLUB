package com.GAAC.GAAC.Backend.DTO.response;

import com.GAAC.GAAC.Backend.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class BlogResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private Instant createdAt;
    private User author;
}
