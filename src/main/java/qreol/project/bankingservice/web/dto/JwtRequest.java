package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Jwt request")
public class JwtRequest {

    @Schema(description = "User login", example = "Tim")
    @NotEmpty(message = "Login must not be empty")
    private String login;

    @Schema(description = "User password", example = "123")
    @NotEmpty(message = "Password must not be empty")
    private String password;

}