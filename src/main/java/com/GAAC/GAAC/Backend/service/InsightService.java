package com.GAAC.GAAC.Backend.service;


import com.GAAC.GAAC.Backend.model.dto.request.InsightDetailsDTO;
import com.GAAC.GAAC.Backend.model.dto.response.InsightResponseDTO;
import com.GAAC.GAAC.Backend.mapper.InsightMapper;
import com.GAAC.GAAC.Backend.model.Insight;
import com.GAAC.GAAC.Backend.model.User;
import com.GAAC.GAAC.Backend.repository.InsightRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class InsightService {

    @Autowired
    private InsightRepo insightRepo;

    @Autowired
    private UserService userService;

    public void saveInsight(InsightDetailsDTO insight, String email) {
        Insight duplicate = insightRepo.findByTitle(insight.getTitle()).orElse(null);
        if(duplicate != null && duplicate.getContent().equals(insight.getContent())) throw new RuntimeException("Blog already exists");
        User user = userService.getUserByEmail(email);
        Insight newInsight = new Insight();
        newInsight.setAuthor(user);
        newInsight.setTitle(insight.getTitle());
        newInsight.setContent(insight.getContent());
        insightRepo.save(newInsight);
        user.getInsightList().add(newInsight);
    }

    public List<InsightResponseDTO> getAllInsights() {
        return insightRepo.findAll()
                .stream()
                .map(InsightMapper::toInsightResponse)
                .toList();
    }

    public Optional<Insight> getInsightById(UUID id) {
        return insightRepo.findById(id);
    }

    public void deleteInsightById(UUID id, String email) {
        User user = userService.getUserByEmail(email);
        user.getInsightList().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        insightRepo.deleteById(id);
    }

    public void updateInsightById(UUID insightId, @Valid InsightDetailsDTO newInsight) {
        Insight old = insightRepo.findById(insightId).orElse(null);
        if(old == null) throw new RuntimeException("User not found");
        old.setTitle(newInsight.getTitle() != null && !newInsight.getTitle().isEmpty() ? newInsight.getTitle() : old.getTitle());
        old.setContent(newInsight.getContent() != null && !newInsight.getContent().isEmpty() ? newInsight.getContent() : old.getContent());
        insightRepo.save(old);
    }
}
