package com.test.demo.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> illegalArgumnetHandler(
        HttpServletRequest r, IllegalArgumentException e) {

        Map<String, String> response = new HashMap<>();

        response.put("message", e.getMessage());
        response.put("code", "BAD_REQUEST");
        return ResponseEntity.badRequest().body(response);
    }
}
