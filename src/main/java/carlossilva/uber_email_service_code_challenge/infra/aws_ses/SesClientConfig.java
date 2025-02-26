package carlossilva.uber_email_service_code_challenge.infra.aws_ses;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class SesClientConfig {
    private final Dotenv dotenv = Dotenv.load();
    private final String region = dotenv.get("AWS_REGION");
    private final String secretAccessKey = dotenv.get("AWS_ACCESS_KEY");
    private final String accessKeyId = dotenv.get("AWS_ACCESS_KEY_ID");

    @Bean
    public SesClient buildSesClient() {
        return SesClient.builder()
                .region(Region.of(region))
                .credentialsProvider(() -> AwsBasicCredentials.builder()
                        .accessKeyId(accessKeyId)
                        .secretAccessKey(secretAccessKey)
                        .build())
                .build();
    }
}
