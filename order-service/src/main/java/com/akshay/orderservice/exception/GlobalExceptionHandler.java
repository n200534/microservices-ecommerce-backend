package com.akshay.orderservice.exception;

import com.akshay.orderservice.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            OrderNotFoundException.class
    )
    public ResponseEntity<ErrorResponse>
    handleOrderNotFound(
            OrderNotFoundException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response =
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Not Found")
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND
        ).body(response);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        String message = "Validation failed";

        if (ex.getBindingResult()
                .getFieldError() != null) {

            message =
                    ex.getBindingResult()
                            .getFieldError()
                            .getDefaultMessage();
        }

        ErrorResponse response =
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Validation Failed")
                        .message(message)
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.badRequest()
                .body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(
            Exception ex,
            HttpServletRequest request
    ) {

        ErrorResponse response =
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                        .error(
                                "Internal Server Error"
                        )
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
        ).body(response);
    }

    @ExceptionHandler(
            InsufficientStockException.class
    )
    public ResponseEntity<ErrorResponse>
    handleInsufficientStock(
            InsufficientStockException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response =
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Insufficient Stock")
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.badRequest()
                .body(response);
    }


    @ExceptionHandler(
            ProductServiceUnavailableException.class
    )
    public ResponseEntity<ErrorResponse>
    handleProductServiceUnavailable(
            ProductServiceUnavailableException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response =
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(
                                HttpStatus.SERVICE_UNAVAILABLE.value()
                        )
                        .error(
                                "Service Unavailable"
                        )
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.status(
                HttpStatus.SERVICE_UNAVAILABLE
        ).body(response);
    }
}
