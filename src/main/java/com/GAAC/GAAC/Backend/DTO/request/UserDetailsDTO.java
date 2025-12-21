package com.GAAC.GAAC.Backend.DTO.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class UserDetailsDTO {
    private String name;
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
    private String AASID;
    @Pattern(
            regexp = "USER|MEMBER|ADMIN",
            message = "Team must be USER or MEMBER OR ADMIN"
    )
    private String role;
    @Pattern(
            regexp = "PROGRAMMERS|STARGAZERS|ROBUSTA|CORE|EB",
            message = "Team must be PROGRAMMERS or STARGAZERS or ROBUSTA or CORE or EB"
    )
    private String team;
    @Pattern(
            regexp = "PRESIDENT|VICE PRESIDENT|SECRETARY|LEAD|MEMBER",
            message = "Team must be PRESIDENT or VICE PRESIDENT or SECRETARY or LEAD or MEMBER"
    )
    private String position;
    private String imageURL;
    private String linkedinUrl;
}
