package carlossilva.uber_email_service_code_challenge.application.services;

import carlossilva.uber_email_service_code_challenge.gateways.EmailSenderGateway;
import carlossilva.uber_email_service_code_challenge.application.services.exceptions.EmailSenderServiceException;
import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.core.usecases.SendEmailUseCase;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailSenderGatewayException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService implements SendEmailUseCase {
    private final List<EmailSenderGateway> emailSenderGateways;

    public EmailSenderService(List<EmailSenderGateway> emailSenderGateways) {
        this.emailSenderGateways = emailSenderGateways;
    }

    @Override
    public void sendEmail(@Valid EmailModel emailModel) {
        for (final EmailSenderGateway gateway : emailSenderGateways) {
            try {
                gateway.sendEmail(emailModel);
                return;
            } catch (EmailSenderGatewayException e) {
                if (gateway == emailSenderGateways.getLast()) {
                    throw new EmailSenderServiceException("All email gateways are down", e);
                }
            }
        }
    }
}
