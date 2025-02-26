package carlossilva.uber_email_service_code_challenge.infra.aws_ses;

import carlossilva.uber_email_service_code_challenge.adapters.EmailSenderGateway;
import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailSenderGatewayException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class SesEmailSender implements EmailSenderGateway {
    private static final Log log = LogFactory.getLog(SesEmailSender.class);
    private final SesClient sesClient;
    private final String verifiedEmail = Dotenv.load().get("VERIFIED_EMAIL");

    public SesEmailSender(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    @Override
    public void sendEmail(@Valid EmailModel emailModel) throws EmailSenderGatewayException {
        log.info("SES - Tentando enviar email: " + emailModel);
        final SendEmailRequest request = SendEmailRequest.builder()
                .source(verifiedEmail)
                .destination(Destination.builder().toAddresses(emailModel.receiver()).build())
                .message(Message.builder()
                                 .body(Body.builder().text(Content.builder().data(emailModel.body()).build()).build())
                                 .subject(Content.builder().data(emailModel.subject()).build())
                                 .build())
                .build();
        try {
            sesClient.sendEmail(request);
        } catch (Exception e) {
            log.error("SES - Erro ao enviar email " + e);
            throw new EmailSenderGatewayException("Falha ao enviar email", e);
        }
    }
}
