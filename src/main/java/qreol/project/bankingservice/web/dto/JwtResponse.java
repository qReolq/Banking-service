package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Jwt response")
public class JwtResponse {

    @Schema(description = "User id", example = "1")
    private Long id;

    @Schema(description = "User login", example = "Tim")
    private String Login;

    @Schema(description = "JWT access token")
    private String accessToken;

    @Schema(description = "JWT refresh token")
    private String refreshToken;
}