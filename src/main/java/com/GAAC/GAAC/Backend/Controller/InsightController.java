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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ProfileResponseDTO user = userService.getUserDTOByEmail(email);
        if(user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<InsightResponseDTO> insights = user.getInsightList();
        if(insights != null && !insights.isEmpty()){
            return new ResponseEntity<>(insights,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new-insight")
    public ResponseEntity<InsightDetailsDTO> createInsight(@Valid @RequestBody InsightDetailsDTO myInsight){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            insightService.saveInsight(myInsight,email);
            return new ResponseEntity<>(myInsight,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(myInsight,HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete-insight/{insightId}")
    public ResponseEntity<?> deleteInsightById(@PathVariable UUID insightId){
        Insight insight = insightService.getInsightById(insightId).orElse(null);
        if(insight == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        insightService.deleteInsightById(insightId,email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-insight/{insightId}")
    public ResponseEntity<?> updateInsightById(@PathVariable UUID insightId,
                                                    @Valid @RequestBody InsightDetailsDTO newInsight){
            insightService.updateInsightById(insightId,newInsight);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
