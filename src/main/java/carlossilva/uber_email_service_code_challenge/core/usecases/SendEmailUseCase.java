package carlossilva.uber_email_service_code_challenge.core.usecases;

import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;

public interface SendEmailUseCase {
    void sendEmail(EmailModel email);
}
