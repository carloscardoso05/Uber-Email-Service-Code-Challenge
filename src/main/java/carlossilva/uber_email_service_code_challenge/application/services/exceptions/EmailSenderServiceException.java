package carlossilva.uber_email_service_code_challenge.application.services.exceptions;

public class EmailSenderServiceException extends RuntimeException{
    public EmailSenderServiceException(String message) {
        super(message);
    }

    public EmailSenderServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
