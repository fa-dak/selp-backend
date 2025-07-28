package org.fadak.selp.selpbackend.exception;

import jakarta.validation.ConstraintViolationException;
import org.fadak.selp.selpbackend.domain.dto.business.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationError(
        MethodArgumentNotValidException ex) {

        StringBuilder sb = new StringBuilder();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            sb.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()));
        }

        ErrorResponseDto body = new ErrorResponseDto("VALIDATION_ERROR", sb.toString().trim());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(
        ConstraintViolationException ex) {

        ErrorResponseDto body = new ErrorResponseDto("VALIDATION_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponseDto> handleFileError(FileStorageException ex) {

        ErrorResponseDto body = new ErrorResponseDto("FILE_ERROR", ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentError(
        IllegalArgumentException ex) {

        ErrorResponseDto body = new ErrorResponseDto("INTERNAL_ERROR", ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }

    // 필요 시 다른 예외 핸들러 추가…
    @ExceptionHandler(GptException.class)
    public ResponseEntity<ErrorResponseDto> handleGptError(GptException ex) {

        ErrorResponseDto body = new ErrorResponseDto("GPT_ERROR", ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorResponseDto> handleMessageError(MessageException ex) {

        ErrorResponseDto body = new ErrorResponseDto("MESSAGE_ERROR", ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }


}