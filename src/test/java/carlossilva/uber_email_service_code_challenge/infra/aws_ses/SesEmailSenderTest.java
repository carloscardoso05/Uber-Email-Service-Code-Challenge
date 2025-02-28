package carlossilva.uber_email_service_code_challenge.infra.aws_ses;

import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailSenderGatewayException;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

import java.io.IOException;

@SpringBootTest
public class SesEmailSenderTest {
    final String receiver = Dotenv.load().get("VERIFIED_EMAIL");

    @MockitoBean
    private final SesClient sesClient;

    private final SesEmailSender sesEmailSender;

    @Autowired
    public SesEmailSenderTest(SesClient sesClient, SesEmailSender sesEmailSender) {
        this.sesClient = sesClient;
        this.sesEmailSender = sesEmailSender;
    }

    @Test
    public void sendEmail_withValidEmail_shouldNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            final EmailModel email = new EmailModel("Email teste", "Olá", receiver);
            sesEmailSender.sendEmail(email);
        });
    }

    @Test
    public void sendEmail_withNotWorkingApi_shouldThrowEmailServiceException() {
        Mockito.when(sesClient.sendEmail(Mockito.<SendEmailRequest>any()))
                .thenThrow(SesException.builder()
                        .message("Fake Ses Exception")
                        .build());
        Assertions.assertThrows(EmailSenderGatewayException.class, () -> {
            final EmailModel email = new EmailModel("", "", "");
            sesEmailSender.sendEmail(email);
        });
    }
}
