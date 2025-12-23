package com.GAAC.GAAC.Backend.Mapper;

import com.GAAC.GAAC.Backend.DTO.response.InsightResponseDTO;
import com.GAAC.GAAC.Backend.Model.Insight;

public class InsightMapper {
    public static InsightResponseDTO toInsightResponse(Insight insight){
        InsightResponseDTO dto = new InsightResponseDTO();
        dto.setId(insight.getId());
        dto.setTitle(insight.getTitle());
        dto.setContent(insight.getContent());
        dto.setCreatedAt(insight.getCreatedAt());
        dto.setAuthorName(
                insight.getAuthor() != null ? insight.getAuthor().getName() : null
        );
        return dto;
    }
}
