package carlossilva.uber_email_service_code_challenge.application.controllers;

import carlossilva.uber_email_service_code_challenge.application.services.EmailSenderService;
import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("send/")
public class EmailSenderController {
    private final EmailSenderService emailSenderService;

    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid EmailModel email) {
        emailSenderService.sendEmail(email);
        return ResponseEntity.ok().build();
    }
}
