package com.GAAC.GAAC.Backend.model.dto.request;

import com.GAAC.GAAC.Backend.model.enums.RecruitmentStatusEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import lombok.Data;

@Data
public class UserSearchCriteriaDTO {
    private RecruitmentStatusEnum recruitmentStatus;
    private Integer yearOfStudy;
    private String searchTerm;
    private TeamEnum team;
}