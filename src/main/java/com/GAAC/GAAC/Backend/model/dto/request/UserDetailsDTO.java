package com.GAAC.GAAC.Backend.model.dto.request;

import com.GAAC.GAAC.Backend.model.enums.PositionEnum;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class UserDetailsDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Collage id must contain exactly 10 digits"
    )
    private String collegeId;
    private String branch;
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must contain exactly 10 digits"
    )
    private String mobileNumber;
    private String yearOfStudy;
    private String description;
    private String AASID;
    private RoleEnum role;
    private TeamEnum team;
    private PositionEnum position;
    private String imageURL;
    private String linkedinUrl;
}
