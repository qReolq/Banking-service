package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdatePhoneNumberRequest {

    @NotEmpty(message = "New phone number must not be empty")
    @Schema(description = "New user phone number. If old phone number is null will add phone number as new", example = "79525205266")
    private String newPhoneNumber;

    @Schema(description = "Old user phone number. If not null will change old phone number to new phone number", example = "79525205266")
    private String oldPhoneNumber;

}
