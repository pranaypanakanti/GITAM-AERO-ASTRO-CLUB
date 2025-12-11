package com.GAAC.GAAC.Backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
public class PublicController {

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Positive";
    }
}
