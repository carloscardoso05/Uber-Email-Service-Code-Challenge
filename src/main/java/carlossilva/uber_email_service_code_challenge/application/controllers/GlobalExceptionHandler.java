package carlossilva.uber_email_service_code_challenge.application.controllers;

import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<Object> handleEmailServiceException(EmailServiceException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
