package com.GAAC.GAAC.Backend.DTO.response;

import com.GAAC.GAAC.Backend.ENUMS.PositionEnum;
import com.GAAC.GAAC.Backend.ENUMS.RoleEnum;
import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProfileResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String collegeId;
    private String branch;
    private String mobileNumber;
    private String yearOfStudy;
    private String AASID;
    private RoleEnum role;
    private TeamEnum team;
    private PositionEnum position;
    private String description;
    private String imageUrl;
    private String linkedinUrl;
    private List<BlogResponseDTO> blogsList;
    private List<InsightResponseDTO> insightList;
}
