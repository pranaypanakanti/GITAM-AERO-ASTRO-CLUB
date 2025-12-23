package com.GAAC.GAAC.Backend.Configuration;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OtpEncoder {

    public String otpEncoder(String email){
        LocalDate today = LocalDate.now();
        String day = String.valueOf(today.getDayOfMonth());
        int firstChAscii = Character.toUpperCase(email.charAt(0));
        return String.valueOf(firstChAscii).concat(day);
    }
}
