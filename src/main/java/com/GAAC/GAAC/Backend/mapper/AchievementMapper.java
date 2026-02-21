package com.GAAC.GAAC.Backend.mapper;

import com.GAAC.GAAC.Backend.model.Achievement;
import com.GAAC.GAAC.Backend.model.dto.response.AchievementResponseDTO;

public class AchievementMapper {
    public static AchievementResponseDTO toAchievementResponse(Achievement achievement){
        AchievementResponseDTO dto = new AchievementResponseDTO();
        dto.setId(achievement.getId());
        dto.setTitle(achievement.getTitle());
        dto.setContent(achievement.getContent());
        dto.setTeam(achievement.getTeam());
        dto.setImageUrl(achievement.getImageUrl());
        dto.setCreatedAt(achievement.getCreatedAt());
        dto.setPriority(achievement.getPriority());
        return dto;
    }
}
