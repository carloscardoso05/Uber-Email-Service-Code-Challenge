package carlossilva.uber_email_service_code_challenge.application.controllers.exception_handling;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Builder
@Value
@JsonInclude(Include.NON_EMPTY)
public class ApiException {
    HttpStatus status;
    String message;
    String path;
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
    Object extraInfo;

    public Integer getStatusCode() {
        return status == null ? null : status.value();
    }

    public ResponseEntity<Object> toResponseEntity() {
        return ResponseEntity.status(getStatus()).body(this);
    }
}
