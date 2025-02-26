package carlossilva.uber_email_service_code_challenge.core.usecases;

import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import jakarta.validation.Valid;

public interface SendEmailUseCase {
    void sendEmail(@Valid EmailModel email);
}
