package com.GAAC.GAAC.Backend.DTO.response;

import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserMiniResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String branch;
    private String yearOfStudy;
    private String AASID;
    private TeamEnum team;
    private String position;
    private String imageUrl;
    private String linkedinUrl;
}
