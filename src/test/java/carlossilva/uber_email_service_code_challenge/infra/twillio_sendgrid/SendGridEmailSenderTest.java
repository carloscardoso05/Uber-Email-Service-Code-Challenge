package carlossilva.uber_email_service_code_challenge.infra.twillio_sendgrid;

import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailServiceException;
import com.sendgrid.SendGrid;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;

@SpringBootTest
public class SendGridEmailSenderTest {
    final String receiver = Dotenv.load().get("VERIFIED_EMAIL");

    @MockitoBean
    private final SendGrid sendGrid;

    private final SendGridEmailSender sendGridEmailSender;

    @Autowired
    public SendGridEmailSenderTest(SendGrid sendGrid, SendGridEmailSender sendGridEmailSender) {
        this.sendGrid = sendGrid;
        this.sendGridEmailSender = sendGridEmailSender;
    }

    @Test
    public void sendEmail_withValidEmail_shouldNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            final EmailModel email = new EmailModel("Email teste", "Olá", receiver);
            sendGridEmailSender.sendEmail(email);
        });
    }

    @Test
    public void sendEmail_withNotWorkingApi_shouldThrowEmailServiceException() throws IOException {
        Mockito.when(sendGrid.api(Mockito.any())).thenThrow(new IOException("Fake IO Exception"));
        Assertions.assertThrows(EmailServiceException.class, () -> {
            final EmailModel email = new EmailModel("", "", "");
            sendGridEmailSender.sendEmail(email);
        });
    }

}
