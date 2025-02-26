package carlossilva.uber_email_service_code_challenge.infra.twillio_sendgrid;

import carlossilva.uber_email_service_code_challenge.gateways.EmailSenderGateway;
import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailSenderGatewayException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class SendGridEmailSender implements EmailSenderGateway {
    private static final Log log = LogFactory.getLog(SendGridEmailSender.class);
    private final SendGrid sendGrid;
    private final String verifiedEmail = Dotenv.load().get("VERIFIED_EMAIL");

    public SendGridEmailSender(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void sendEmail(@Valid EmailModel email) throws EmailSenderGatewayException {
        log.info("SendGrid - Tentando enviar email: " + email);
        final Email from = new Email(verifiedEmail);
        final Email to = new Email(email.receiver());
        final String subject = email.subject();
        final Content body = new Content("text/plain", email.body());
        final Mail mail = new Mail(from, subject, to, body);
        final Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (Exception e) {
            log.error("SendGrid - Erro ao enviar email " + e);
            throw new EmailSenderGatewayException("Falha ao enviar email", e);
        }
    }
}
