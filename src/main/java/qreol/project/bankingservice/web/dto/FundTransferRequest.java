package qreol.project.bankingservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Fund transfer request")
public class FundTransferRequest {

    @Schema(description = "Sender account id", example = "1")
    @NotNull(message = "From account id must not be null")
    @Min(value = 1, message = "From account id must be greater then 1")
    private Long fromAccountId;

    @Schema(description = "Recipient account id", example = "2")
    @NotNull(message = "To account id must not be null")
    @Min(value = 1, message = "To account id must be greater then 1")
    private Long toAccountId;

    @Schema(description = "Amount of money", example = "100")
    @NotNull(message = "Amount must not be null")
    @Min(value = 10, message = "Amount must be greater then 10")
    private BigDecimal amount;

}
