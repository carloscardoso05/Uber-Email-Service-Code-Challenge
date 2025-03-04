package carlossilva.uber_email_service_code_challenge.core.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailModel(
        @NotBlank(message = "The subject can't be empty")
        String subject,
        @NotBlank(message = "The body can't be empty")
        String body,
        @NotBlank(message = "The receiver email can't be empty")
        @Email(message = "The receiver email is invalid")
        String receiver
) {
}
