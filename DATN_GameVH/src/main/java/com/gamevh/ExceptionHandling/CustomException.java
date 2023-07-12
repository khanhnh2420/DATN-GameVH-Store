package com.gamevh.ExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class CustomException extends RuntimeException{
    private String message;
    private HttpStatus status;

}
