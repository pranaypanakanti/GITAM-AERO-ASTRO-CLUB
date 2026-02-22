package com.GAAC.GAAC.Backend.model.dto.request;

import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Collage id must contain exactly 10 digits"
    )
    private String collegeId;
    @NotBlank
    private String branch;
    @NotBlank
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must contain exactly 10 digits"
    )
    private String mobileNumber;
    @NotBlank
    private String yearOfStudy;
    @NotNull
    private TeamEnum team;
}
