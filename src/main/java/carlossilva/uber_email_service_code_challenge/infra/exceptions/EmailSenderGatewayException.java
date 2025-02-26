package carlossilva.uber_email_service_code_challenge.infra.exceptions;

public class EmailSenderGatewayException extends RuntimeException {
    public EmailSenderGatewayException(String message) {
        super(message);
    }

    public EmailSenderGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
