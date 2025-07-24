package org.fadak.selp.selpbackend.exception;

import org.fadak.selp.selpbackend.domain.dto.business.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
}