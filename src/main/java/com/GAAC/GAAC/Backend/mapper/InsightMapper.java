package com.GAAC.GAAC.Backend.mapper;

import com.GAAC.GAAC.Backend.model.dto.response.InsightResponseDTO;
import com.GAAC.GAAC.Backend.model.Insight;

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
