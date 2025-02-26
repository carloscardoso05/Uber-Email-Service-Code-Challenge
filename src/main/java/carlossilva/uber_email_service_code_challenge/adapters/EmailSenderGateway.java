package carlossilva.uber_email_service_code_challenge.adapters;

import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailSenderGatewayException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public interface EmailSenderGateway {
    void sendEmail(@Valid EmailModel email) throws EmailSenderGatewayException;
}
