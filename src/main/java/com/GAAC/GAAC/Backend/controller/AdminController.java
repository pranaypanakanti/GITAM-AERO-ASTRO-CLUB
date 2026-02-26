package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.MailContent;
import com.GAAC.GAAC.Backend.model.dto.request.UserSearchCriteriaDTO;
import com.GAAC.GAAC.Backend.model.dto.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.*;
import com.GAAC.GAAC.Backend.service.EmailService;
import com.GAAC.GAAC.Backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @Operation(
            summary = "Get all users",
            description = "ADMIN only. Returns all user details"
    )
    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<UserMiniResponseDTO> users = userService.getAllUsers();
            if(users.isEmpty()) throw new RuntimeException("Users not found");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Change users role",
            description = "ADMIN only. Updates user role by user id"
    )
    @GetMapping("/change-role/{role}/{userId}")
    public ResponseEntity<?> changeRole(@Valid @PathVariable RoleEnum role, @PathVariable UUID userId){
        try {
            userService.changeRole(role,userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/change-recruitment-status/{status}/{userId}")
    public ResponseEntity<?> changeRole(@Valid @PathVariable RecruitmentStatusEnum status, @PathVariable UUID userId){
        try {
            userService.changeRecruitmentStatus(status,userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Delete user",
            description = "ADMIN only. Deletes user by user id"
    )
    @DeleteMapping("/delete-profile-by-id/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId){
        try{
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Reset recruitment status",
            description = "ADMIN only. Changes all users recruitment status to not applied"
    )
    @GetMapping("/reset-recruitment-status")
    public ResponseEntity<?> resetRecruitmentDetails(){
        try {
            userService.resetRecruitmentDetails();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Get all filtered users")
    @GetMapping("/filter-users")
    public ResponseEntity<?> filterUsers(
            @RequestParam(required = false) RecruitmentStatusEnum recruitmentStatus,
            @RequestParam(required = false) Integer yearOfStudy,
            @RequestParam(required = false) TeamEnum team,
            @RequestParam(required = false) RoleEnum role,
            @RequestParam(required = false) PositionEnum position,
            @RequestParam(required = false) String searchTerm) {

        try {
            UserSearchCriteriaDTO criteria = new UserSearchCriteriaDTO();
            criteria.setRecruitmentStatus(recruitmentStatus);
            criteria.setYearOfStudy(yearOfStudy);
            criteria.setTeam(team);
            criteria.setRole(role);
            criteria.setPosition(position);
            criteria.setSearchTerm(searchTerm);

            List<UserMiniResponseDTO> users = userService.searchUsers(criteria);

            return ResponseEntity.ok(users);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Filter failed: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Update mail content",
            description = "Update mail content by id"
    )
    @PutMapping("/update-mail-content/{mailContentTitle}")
    public ResponseEntity<?> updateMailContentById(@PathVariable @Valid MailContentEnum mailContentTitle,
                                                   @Valid @RequestBody MailContent newMailContent){
        try {
            emailService.updateMailContentById(mailContentTitle,newMailContent);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/new-mail-content")
    public ResponseEntity<?> createMailContent(@Valid @RequestBody MailContent newMailContent){
        try{
            emailService.saveMailContent(newMailContent);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
