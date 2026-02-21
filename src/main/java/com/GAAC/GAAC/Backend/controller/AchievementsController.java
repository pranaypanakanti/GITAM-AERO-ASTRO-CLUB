package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.Achievement;
import com.GAAC.GAAC.Backend.model.dto.request.AchievementDetailsDTO;
import com.GAAC.GAAC.Backend.service.AchievementService;
import com.GAAC.GAAC.Backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/achievement")
public class AchievementsController {
    @Autowired
    private AchievementService achievementService;

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Post achievement",
            description = "Create a new achievement"
    )
    @PostMapping("/new-achievement")
    public ResponseEntity<AchievementDetailsDTO> createAchievement(@Valid @RequestBody AchievementDetailsDTO myAchievement){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            achievementService.saveAchievement(myAchievement);
            return new ResponseEntity<>(myAchievement,HttpStatus.CREATED);
        }catch (RuntimeException e){
            throw  new RuntimeException("Achievement already exists");
        }catch (Exception e){
            return new ResponseEntity<>(myAchievement,HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(
            summary = "Delete achievement",
            description = "Deletes achievement by id"
    )
    @DeleteMapping("/delete-achievement/{achievementId}")
    public ResponseEntity<?> deleteAchievementById(@PathVariable UUID achievementId){
        try {
            Achievement achievement = achievementService.getAchievementById(achievementId).orElse(null);
            if(achievement == null) throw new RuntimeException("Achievement not found");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            achievementService.deleteAchievementById(achievementId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Update achievement",
            description = "Update achievement by id"
    )
    @PutMapping("/update-achievement/{achievementId}")
    public ResponseEntity<?> updateAchievementById(@PathVariable UUID achievementId,
                                                    @Valid @RequestBody AchievementDetailsDTO newAchievement){
            try {
                achievementService.updateAchievementById(achievementId,newAchievement);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
    }
}
