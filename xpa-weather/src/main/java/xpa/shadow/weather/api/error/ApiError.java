package xpa.shadow.weather.api.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private OffsetDateTime timestamp = OffsetDateTime.now(ZoneOffset.UTC);
    private int status;
    private String error;
    private String message;
    private String path;

    public void setHttpStatus(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
    }
}
