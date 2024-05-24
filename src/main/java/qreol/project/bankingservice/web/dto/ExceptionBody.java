package qreol.project.bankingservice.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Exception body")
public class ExceptionBody {

    @Schema(description = "Error message", example = "User not found")
    private String message;

    @Schema(description = "Map of errors", example = "\"login\": \"Login must not be empty\"")
    private Map<String, String> errors = new HashMap<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Timestamp", example = "2023-04-31T00:00")
    private LocalDateTime timestamp;

    public ExceptionBody(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}