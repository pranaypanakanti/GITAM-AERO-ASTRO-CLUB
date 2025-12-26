package com.GAAC.GAAC.Backend.model;

import com.GAAC.GAAC.Backend.model.enums.PositionEnum;
import com.GAAC.GAAC.Backend.model.enums.RecruitmentStatusEnum;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(columnDefinition = "uuid")
    private UUID id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String collegeId;
    private String branch;
    private String mobileNumber;
    private String yearOfStudy;
    private String AASID;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    private String linkedinUrl;
    private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private TeamEnum team;
    @Enumerated(EnumType.STRING)
    private PositionEnum position;
    @Enumerated(EnumType.STRING)
    private RecruitmentStatusEnum recruitmentStatus;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Blog> blogsList = new ArrayList<>();
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Insight> insightList = new ArrayList<>();
}
