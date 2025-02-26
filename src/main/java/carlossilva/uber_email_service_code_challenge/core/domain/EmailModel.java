package carlossilva.uber_email_service_code_challenge.core.domain;

import jakarta.validation.constraints.NotBlank;

public record EmailModel(
        String subject,
        String body,
        @NotBlank @jakarta.validation.constraints.Email
        String receiver
) {
}
