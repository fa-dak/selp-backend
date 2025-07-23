package org.fadak.selp.selpbackend.exception;

import org.fadak.selp.selpbackend.domain.dto.business.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileError(FileStorageException ex) {

        ErrorResponse body = new ErrorResponse("FILE_ERROR", ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }

    // 필요 시 다른 예외 핸들러 추가…
}