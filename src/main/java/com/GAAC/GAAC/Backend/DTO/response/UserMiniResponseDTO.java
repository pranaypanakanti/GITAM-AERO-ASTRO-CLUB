package com.GAAC.GAAC.Backend.DTO.response;

import com.GAAC.GAAC.Backend.ENUMS.PositionEnum;
import com.GAAC.GAAC.Backend.ENUMS.RoleEnum;
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
    private String collegeId;
    private String mobileNumber;
    private RoleEnum role;
    private String branch;
    private String description;
    private String yearOfStudy;
    private String AASID;
    private TeamEnum team;
    private PositionEnum position;
    private String imageUrl;
    private String linkedinUrl;
}
