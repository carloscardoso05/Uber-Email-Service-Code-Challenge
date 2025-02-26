package carlossilva.uber_email_service_code_challenge.application.controllers.exception_handling;

import carlossilva.uber_email_service_code_challenge.application.services.exceptions.EmailSenderServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailSenderServiceException.class)
    public ResponseEntity<Object> handleEmailServiceException(EmailSenderServiceException e) {
        return ApiException.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Sorry, we are unable to send the email at the moment. Please try again later")
                .build()
                .toResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final Map<String, Map<String, String>> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            final String fieldName = ((FieldError) error).getField();
            final String errorMessage = error.getDefaultMessage();
            String rejectedValue = null;

            if (((FieldError) error).getRejectedValue() != null) {
                rejectedValue = ((FieldError) error).getRejectedValue().toString();
            }

            final Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", errorMessage);
            errorMap.put("rejectedValue", rejectedValue);
            errors.put(fieldName, errorMap);
        });
        return ApiException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid request body")
                .extraInfo(errors)
                .build().toResponseEntity();
    }
}
