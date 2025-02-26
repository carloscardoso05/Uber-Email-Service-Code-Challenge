package carlossilva.uber_email_service_code_challenge.application.controllers;

import carlossilva.uber_email_service_code_challenge.core.domain.EmailModel;
import carlossilva.uber_email_service_code_challenge.infra.aws_ses.SesEmailSender;
import carlossilva.uber_email_service_code_challenge.infra.exceptions.EmailSenderGatewayException;
import carlossilva.uber_email_service_code_challenge.infra.twillio_sendgrid.SendGridEmailSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailSenderControllerTest {
    @MockitoBean
    private final SesEmailSender sesEmailSender;
    @MockitoBean
    private final SendGridEmailSender sendGridEmailSender;
    private final String URL = "/send/";
    private final String verifiedEmail = Dotenv.load().get("VERIFIED_EMAIL");
    private final MockMvc mockMvc;

    @Autowired
    public EmailSenderControllerTest(SesEmailSender sesEmailSender, SendGridEmailSender sendGridEmailSender, MockMvc mockMvc) {
        this.sesEmailSender = sesEmailSender;
        this.sendGridEmailSender = sendGridEmailSender;
        this.mockMvc = mockMvc;
    }

    @Test
    public void sendEmail_onNormalConditions_shouldHaveStatusOk() throws Exception {
        final EmailModel email = new EmailModel("Email teste", "Teste de email", verifiedEmail);
        final String json = new ObjectMapper().writeValueAsString(email);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void sendEmail_withEmptyReceiver_shouldHaveStatusBadRequest() throws Exception {
        final EmailModel email = new EmailModel("Email teste", "Teste de email", "");
        final String json = new ObjectMapper().writeValueAsString(email);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.extraInfo.receiver.message").value("The receiver email can't be empty"))
                .andExpect(jsonPath("$.extraInfo.receiver.rejectedValue").value(email.receiver()));
    }

    @Test
    public void sendEmail_withInvalidReceiver_shouldHaveStatusBadRequest() throws Exception {
        final EmailModel email = new EmailModel("Email teste", "Teste de email", "invalidEmail");
        final String json = new ObjectMapper().writeValueAsString(email);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.extraInfo.receiver.message").value("The receiver email is invalid"))
                .andExpect(jsonPath("$.extraInfo.receiver.rejectedValue").value(email.receiver()));
    }

    @Test
    public void sendEmail_withNullReceiver_shouldHaveStatusBadRequest() throws Exception {
        final EmailModel email = new EmailModel("Email teste", "Teste de email", null);
        final String json = new ObjectMapper().writeValueAsString(email);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.extraInfo.receiver.message").value("The receiver email can't be empty"))
                .andExpect(jsonPath("$.extraInfo.receiver.rejectedValue").value(nullValue()));
    }

    @Test
    public void sendEmail_withBothEmailGatewaysDown_shouldHaveStatusInternalServerError() throws Exception {
        Mockito.doThrow(new EmailSenderGatewayException("Ses email sender gateway is down"))
                .when(sesEmailSender)
                .sendEmail(Mockito.any());
        Mockito.doThrow(new EmailSenderGatewayException("SendGrid email sender gateway is down"))
                .when(sendGridEmailSender)
                .sendEmail(Mockito.any());

        final EmailModel email = new EmailModel("Email teste", "Teste de email", verifiedEmail);
        final String json = new ObjectMapper().writeValueAsString(email);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Sorry, we are unable to send the email at the moment. Please try again later"));
    }

    @Test
    public void sendEmail_withSesGatewayDown_shouldHaveStatusOk() throws Exception {
        Mockito.doThrow(new EmailSenderGatewayException("Ses email sender gateway is down"))
                .when(sesEmailSender)
                .sendEmail(Mockito.any());

        final EmailModel email = new EmailModel("Email teste", "Teste de email", verifiedEmail);
        final String json = new ObjectMapper().writeValueAsString(email);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void sendEmail_withSendGridGatewayDown_shouldHaveStatusOk() throws Exception {
        Mockito.doThrow(new EmailSenderGatewayException("SendGrid email sender gateway is down"))
                .when(sendGridEmailSender)
                .sendEmail(Mockito.any());

        final EmailModel email = new EmailModel("Email teste", "Teste de email", verifiedEmail);
        final String json = new ObjectMapper().writeValueAsString(email);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
