package com.GAAC.GAAC.Backend.model.dto.response;

import com.GAAC.GAAC.Backend.model.enums.PositionEnum;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
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
