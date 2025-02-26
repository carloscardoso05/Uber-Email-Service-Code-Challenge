package carlossilva.uber_email_service_code_challenge.application.services;

import carlossilva.uber_email_service_code_challenge.application.services.exceptions.EmailSenderServiceException;
import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.infra.aws_ses.SesEmailSender;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailSenderGatewayException;
import carlossilva.uber_email_service_code_challenge.infra.twillio_sendgrid.SendGridEmailSender;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class EmailSenderServiceTest {
    @MockitoBean
    private final SesEmailSender sesEmailSender;
    @MockitoBean
    private final SendGridEmailSender sendGridEmailSender;
    private final EmailSenderService emailSenderService;
    private final String verifiedEmail = Dotenv.load().get("VERIFIED_EMAIL");

    @Autowired
    public EmailSenderServiceTest(SesEmailSender sesEmailSender, SendGridEmailSender sendGridEmailSender, EmailSenderService emailSenderService) {
        this.sesEmailSender = sesEmailSender;
        this.sendGridEmailSender = sendGridEmailSender;
        this.emailSenderService = emailSenderService;
    }

    @Test
    public void sendEmail_onNormalConditions_shouldNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            final EmailModel email = new EmailModel("Email teste", "Olá", verifiedEmail);
            emailSenderService.sendEmail(email);
        });
    }

    @Test
    public void sendEmail_whenSesIsNotWorking_shouldNotThrow() {
        Mockito.doThrow(new EmailSenderGatewayException("Ses is not working"))
                .when(sesEmailSender)
                .sendEmail(Mockito.any());

        Assertions.assertDoesNotThrow(() -> {
            final EmailModel email = new EmailModel("Email teste", "Olá", verifiedEmail);
            emailSenderService.sendEmail(email);
        });
    }

    @Test
    public void sendEmail_whenSendGridIsNotWorking_shouldNotThrow() {
        Mockito.doThrow(new EmailSenderGatewayException("SendGrid is not working"))
                .when(sendGridEmailSender)
                .sendEmail(Mockito.any());

        Assertions.assertDoesNotThrow(() -> {
            final EmailModel email = new EmailModel("Email teste", "Olá", verifiedEmail);
            emailSenderService.sendEmail(email);
        });
    }

    @Test
    public void sendEmail_whenSesAndSendGridIsNotWorking_shouldThrowEmailSenderServiceException() {
        Mockito.doThrow(new EmailSenderGatewayException("SendGrid is not working"))
                .when(sendGridEmailSender)
                .sendEmail(Mockito.any());

        Mockito.doThrow(new EmailSenderGatewayException("Ses is not working"))
                .when(sesEmailSender)
                .sendEmail(Mockito.any());

        Assertions.assertThrows(EmailSenderServiceException.class, () -> {
            final EmailModel email = new EmailModel("Email teste", "Olá", verifiedEmail);
            emailSenderService.sendEmail(email);
        });
    }
}
