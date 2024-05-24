package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "Register user request")
public class RegisterUserRequest {

    @Schema(description = "User login", example = "Tim")
    @NotEmpty(message = "Login must not be empty")
    private String login;

    @Schema(description = "User password", example = "123")
    @NotEmpty(message = "Password must not be empty")
    private String password;

    @Schema(description = "User phone number", example = "79625205242")
    @NotEmpty(message = "PhoneNumber must not be empty")
    private String phoneNumber;

    @Schema(description = "User email", example = "stim@gmail.com")
    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    @Schema(description = "User initial deposit", example = "100")
    @Min(value = 0, message = "Initial deposit must be greater than zero")
    private BigDecimal initialDeposit;

    @Schema(description = "User date of birth", example = "2023-04-31")
    private LocalDate dateOfBirth;

    @Schema(description = "User full name", example = "Jack Samuel Adams")
    private String fullName;


}
