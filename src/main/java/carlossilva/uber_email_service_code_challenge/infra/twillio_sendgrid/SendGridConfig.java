package carlossilva.uber_email_service_code_challenge.infra.twillio_sendgrid;

import com.sendgrid.SendGrid;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {
    private final String apiKey = Dotenv.load().get("SENDGRID_API_KEY");

    @Bean
    SendGrid getSendGrid() {
        return new SendGrid(apiKey);
    }
}
