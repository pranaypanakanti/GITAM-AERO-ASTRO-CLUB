package com.GAAC.GAAC.Backend.service;


import com.GAAC.GAAC.Backend.mapper.AchievementMapper;
import com.GAAC.GAAC.Backend.model.Achievement;
import com.GAAC.GAAC.Backend.model.dto.request.AchievementDetailsDTO;
import com.GAAC.GAAC.Backend.model.dto.response.AchievementResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.repository.AchievementRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AchievementService {

    @Autowired
    private AchievementRepo achievementRepo;


    @PreAuthorize("hasAnyRole('ADMIN')")
    public void saveAchievement(AchievementDetailsDTO achievement) {
        Achievement duplicate = achievementRepo.findByTitle(achievement.getTitle()).orElse(null);
        if(duplicate != null && duplicate.getContent().equals(achievement.getContent())) throw new RuntimeException("Achievement already exists");
        Achievement newAchievement = new Achievement();
        newAchievement.setTitle(achievement.getTitle());
        newAchievement.setContent(achievement.getContent());
        newAchievement.setTeam(achievement.getTeam());
        newAchievement.setImageUrl(achievement.getImageUrl());
        achievementRepo.save(newAchievement);
    }

    public List<AchievementResponseDTO> findAllAchievementsByPriority() {
        return achievementRepo.findAllOrderedByPriority()
                .stream()
                .map(AchievementMapper::toAchievementResponse)
                .toList();
    }

    public List<AchievementResponseDTO> getTeamAchievements(TeamEnum teamName) {
        return achievementRepo.findByTeamOrderByPriority(teamName)
                .stream()
                .map(AchievementMapper::toAchievementResponse)
                .toList();
    }

    public Optional<Achievement> getAchievementById(UUID id) {
        return achievementRepo.findById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteAchievementById(UUID id) {
        achievementRepo.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void updateAchievementById(UUID achievementId, @Valid AchievementDetailsDTO newAchievement) {
        try{
            Achievement old = achievementRepo.findById(achievementId).orElse(null);
            if(old == null) throw new RuntimeException("User not found");
            old.setTitle(newAchievement.getTitle() != null && !newAchievement.getTitle().isEmpty() ? newAchievement.getTitle() : old.getTitle());
            old.setContent(newAchievement.getContent() != null && !newAchievement.getContent().isEmpty() ? newAchievement.getContent() : old.getContent());
            old.setTeam(newAchievement.getTeam() != null ? newAchievement.getTeam() : old.getTeam());
            old.setImageUrl(newAchievement.getImageUrl() != null ? newAchievement.getImageUrl() : old.getImageUrl());
            achievementRepo.save(old);
        }catch (Exception e) {
            throw new RuntimeException("Failed to update achievement", e);
        }
    }
}
