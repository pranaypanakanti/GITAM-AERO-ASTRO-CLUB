package com.GAAC.GAAC.Backend.exceptions;

import com.GAAC.GAAC.Backend.model.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                "BAD_REQUEST",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponseDTO error = new ErrorResponseDTO(
                "VALIDATION_FAILED",
                "One or more fields have invalid values.",
                fieldErrors
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleEnumError(HttpMessageNotReadableException ex) {
        String message = "The request body is malformed or contains invalid values.";
        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException formatEx) {
            String fieldName = formatEx.getPath().isEmpty() ? "unknown" : formatEx.getPath().get(0).getFieldName();
            message = String.format("Invalid value for '%s'. Accepted values are: %s",
                    fieldName,
                    java.util.Arrays.toString(formatEx.getTargetType().getEnumConstants()));
        }

        ErrorResponseDTO error = new ErrorResponseDTO(
                "INVALID_INPUT_FORMAT",
                message,
                null
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOtp(InvalidOtpException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "OTP_INCORRECT");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
