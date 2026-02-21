package com.GAAC.GAAC.Backend.service;

import com.GAAC.GAAC.Backend.configuration.OtpEncoder;
import com.GAAC.GAAC.Backend.model.User;
import com.GAAC.GAAC.Backend.model.dto.request.UserSighInDTO;
import com.GAAC.GAAC.Backend.model.dto.response.AuthResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.exceptions.InvalidOtpException;
import com.GAAC.GAAC.Backend.repository.UserRepo;
import com.GAAC.GAAC.Backend.utilis.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;
import java.util.logging.ErrorManager;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OtpEncoder otpEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SendGridEmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private OtpEncoder optEncoder;

    @Transactional
    public void sendOtpForSignIn(String email) {
        try{
            if (userRepo.findByEmail(email).orElse(null) != null) {
                throw new IllegalArgumentException("User with this email already exists");
            }
            String otp = otpEncoder.otpEncoder(email);

            String subject = "Verify Your Account - GITAM Aero Astro Club \uD83D\uDE80";
            String body = "Welcome to the Skies!\n" +
                    "Hello,\n\n" +
                    "Thank you for joining the GITAM Aero Astro Club! We are excited to have you on board.\n\n" +
                    "To complete your registration, please use the following One-Time Password (OTP):\n\n" +
                    "OTP: " + otp + "\n\n" +
                    "This code is valid for the next 10 minutes. Please do not share this code with anyone.\n\n" +
                    "Team GAAC\n";

            emailService.sendEmail(email, subject, body);
        }catch (Exception e) {
            log.error("❌ OTP sending failed for {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to send OTP", e);
        }
    }

    public void changePassword(UserSighInDTO user){
        User oldUser = userService.getUserByEmail(user.getEmail());
        oldUser.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(oldUser);
    }

    public AuthResponseDTO signUpWithOtp(UserSighInDTO signUpRequest) {
        validateOtp(signUpRequest.getEmail(), signUpRequest.getOtp());

        if (userRepo.findByEmail(signUpRequest.getEmail()).orElse(null) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User newUser = createBasicUser(signUpRequest);
        User savedUser = userRepo.save(newUser);

        String accessToken = jwtUtil.generateAccessToken(savedUser.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(savedUser.getEmail());

        return buildAuthResponse(savedUser, accessToken, refreshToken);
    }

    public void resetPasswordWithOtp(UserSighInDTO request) {
        validateOtp(request.getEmail(), request.getOtp());

        User user = userService.getUserByEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.saveUser(user);
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // Set true in production with HTTPS
                .path("/api/auth/refresh")
                .maxAge(Duration.ofDays(30).getSeconds())
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie createLogoutCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh")
                .maxAge(0) // Delete cookie
                .sameSite("Strict")
                .build();
    }


    private void validateOtp(String email, String givenOtp) {
        String correctOtp = otpEncoder.otpEncoder(email);
        if (!givenOtp.equals(correctOtp)) {
            throw new InvalidOtpException("The OTP you entered is incorrect. Please try again.");
        }
    }

    private User createBasicUser(UserSighInDTO signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(RoleEnum.USER);
        return user;
    }

    private AuthResponseDTO buildAuthResponse(User user, String accessToken, String refreshToken) {
        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}