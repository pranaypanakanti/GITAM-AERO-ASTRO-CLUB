package com.GAAC.GAAC.Backend.model.dto.response;

import com.GAAC.GAAC.Backend.model.enums.PositionEnum;
import com.GAAC.GAAC.Backend.model.enums.RecruitmentStatusEnum;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String collegeId;
    private String branch;
    private String mobileNumber;
    private String yearOfStudy;
    private RecruitmentStatusEnum recruitmentStatus;
    private TeamEnum team;
    private String linkedinUrl;
    private String description;
    private RoleEnum role;
    private PositionEnum position;
}