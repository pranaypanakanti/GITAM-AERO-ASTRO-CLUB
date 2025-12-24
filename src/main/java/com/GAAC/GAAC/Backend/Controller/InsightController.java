package com.GAAC.GAAC.Backend.Controller;

import com.GAAC.GAAC.Backend.DTO.request.InsightDetailsDTO;
import com.GAAC.GAAC.Backend.DTO.response.InsightResponseDTO;
import com.GAAC.GAAC.Backend.DTO.response.ProfileResponseDTO;
import com.GAAC.GAAC.Backend.Model.Insight;
import com.GAAC.GAAC.Backend.Service.InsightService;
import com.GAAC.GAAC.Backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/insight")
public class InsightController {
    @Autowired
    private InsightService insightService;

    @Autowired
    private UserService userService;


    @GetMapping("/get-by-user")
    public ResponseEntity<?> getAllInsightsOfUser(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            ProfileResponseDTO user = userService.getUserDTOByEmail(email);
            if(user == null) throw new RuntimeException("User not found");
            List<InsightResponseDTO> insights = user.getInsightList();
            if(insights != null && !insights.isEmpty()){
                return new ResponseEntity<>(insights,HttpStatus.OK);
            }else throw new RuntimeException("No data found.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/new-insight")
    public ResponseEntity<InsightDetailsDTO> createInsight(@Valid @RequestBody InsightDetailsDTO myInsight){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            insightService.saveInsight(myInsight,email);
            return new ResponseEntity<>(myInsight,HttpStatus.CREATED);
        }catch (RuntimeException e){
            throw  new RuntimeException(e.getMessage());
        }
    }


    @DeleteMapping("/delete-insight/{insightId}")
    public ResponseEntity<?> deleteInsightById(@PathVariable UUID insightId){
        try{
            Insight insight = insightService.getInsightById(insightId).orElse(null);
            if(insight == null) throw new RuntimeException("Blog not found");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            insightService.deleteInsightById(insightId,email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update-insight/{insightId}")
    public ResponseEntity<?> updateInsightById(@PathVariable UUID insightId,
                                                    @Valid @RequestBody InsightDetailsDTO newInsight){
            try {
                insightService.updateInsightById(insightId,newInsight);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
    }
}
