package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Remove email request")
public class RemoveEmailRequest {

    @Schema(description = "Email that will be deleted", example = "stim@gmail.com")
    @NotEmpty(message = "New email must not be empty")
    private String email;


}
