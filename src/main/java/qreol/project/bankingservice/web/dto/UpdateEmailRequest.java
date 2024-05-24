package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Update email request")
public class UpdateEmailRequest {

    @Email
    @NotEmpty(message = "New email must not be empty")
    @Schema(description = "New user email. If old Email is null will add emails as new", example = "stim@gmail.com")
    private String newEmail;

    @Email
    @Schema(description = "Old user email. If not null will change old email to new email", example = "stim@gmail.com")
    private String oldEmail;

}
