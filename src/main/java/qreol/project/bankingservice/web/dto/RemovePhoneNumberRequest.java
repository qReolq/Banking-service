package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Phone number that will be deleted")
public class RemovePhoneNumberRequest {

    @Schema(description = "Phone number that will be deleted", example = "79625205242")
    @NotEmpty(message = "Phone number must not be empty")
    private String phoneNumber;

}
