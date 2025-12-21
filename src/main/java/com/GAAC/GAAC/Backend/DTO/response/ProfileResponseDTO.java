package com.GAAC.GAAC.Backend.DTO.response;

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
    private String role;
    private String team;
    private String position;
    private String imageUrl;
    private String linkedinUrl;
    private List<BlogResponseDTO> blogsList;
}
