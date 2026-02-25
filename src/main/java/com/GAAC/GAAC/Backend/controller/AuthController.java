package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.User;
import com.GAAC.GAAC.Backend.model.dto.request.LoginRequestDTO;
import com.GAAC.GAAC.Backend.model.dto.request.MailDTO;
import com.GAAC.GAAC.Backend.model.dto.request.UserSighInDTO;
import com.GAAC.GAAC.Backend.model.dto.response.AuthResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.MailContentEnum;
import com.GAAC.GAAC.Backend.service.AuthService;
import com.GAAC.GAAC.Backend.service.UserService;
import com.GAAC.GAAC.Backend.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserService userService;
    @Autowired private AuthService authService;

    @Operation(
            summary = "Send OTP for sign-in",
            description = "Step-1: Email is sent to user inbox"
    )
    @PostMapping("/send-otp/{reason}")
    public ResponseEntity<?> sendOtp(@PathVariable MailContentEnum reason, @Valid @RequestBody MailDTO emailDTO) {
        try {
            authService.sendOtp(reason, emailDTO.getEmail());
            return ResponseEntity.ok().body("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Sign-up with OTP",
            description = "Step-2: Creates account with OTP verification and returns JWT tokens"
    )
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSighInDTO user) {
        try {
            AuthResponseDTO authResponse = authService.signUpWithOtp(user);

            ResponseCookie refreshTokenCookie = authService.createRefreshTokenCookie(
                    authResponse.getRefreshToken()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(authResponse);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Login with email and password")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .email(userDetails.getUsername())
                    .build();

            ResponseCookie refreshTokenCookie = authService.createRefreshTokenCookie(refreshToken);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(responseDTO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }

    @Operation(summary = "Refresh access token")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token missing");
        }

        try {
            if (!jwtUtil.isRefreshToken(refreshToken) || jwtUtil.isTokenExpired(refreshToken)) {
                ResponseCookie logoutCookie = authService.createLogoutCookie();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .header(HttpHeaders.SET_COOKIE, logoutCookie.toString())
                        .body("Invalid or expired refresh token");
            }

            String username = jwtUtil.extractUsername(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(username);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);

            AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .email(username)
                    .build();

            ResponseCookie newCookie = authService.createRefreshTokenCookie(newRefreshToken);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, newCookie.toString())
                    .body(responseDTO);

        } catch (Exception e) {
            ResponseCookie logoutCookie = authService.createLogoutCookie();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, logoutCookie.toString())
                    .body("Invalid refresh token");
        }
    }

    @Operation(
            summary = "Forget password",
            description = "Reset password using OTP"
    )
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody UserSighInDTO user) {
        try {
            authService.resetPasswordWithOtp(user);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie logoutCookie = authService.createLogoutCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, logoutCookie.toString())
                .body("Logged out successfully");
    }

}