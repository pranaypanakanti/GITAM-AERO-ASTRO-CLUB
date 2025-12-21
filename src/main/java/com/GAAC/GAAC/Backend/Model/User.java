package com.GAAC.GAAC.Backend.Model;

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
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "collegeId", nullable = false)
    private String collegeId;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "branch")
    private String branch;
    @Column(name = "mobileNumber")
    private String mobileNumber;
    @Column(name = "yearOfStudy")
    private String yearOfStudy;
    @Column(name = "AASID")
    private String AASID;
    @Column(name = "role")
    private String role;
    @Column(name = "linkedin_url")
    private String linkedinUrl;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "team")
    private String team;
    @Column(name = "position")
    private String position;
    @Column(name = "recruitmentStatus")
    private String recruitmentStatus;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Blog> blogsList = new ArrayList<>();
}
