package qreol.project.bankingservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qreol.project.bankingservice.domain.Transaction;
import qreol.project.bankingservice.service.BankingService;
import qreol.project.bankingservice.web.dto.FundTransferRequest;
import qreol.project.bankingservice.web.validator.AccountValidator;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Tag(name = "Account", description = "Account API")
public class AccountController {

    private final BankingService bankingService;
    private final AccountValidator accountValidator;

    @Operation(summary = "Transfer money")
    @PostMapping("/transfer")
    @PreAuthorize("@customSecurityExpression.canAccessUserById(#fundTransferRequest.fromAccountId)")
    public ResponseEntity<Transaction> transferMoney(
            @RequestBody @Validated FundTransferRequest fundTransferRequest,
            BindingResult bindingResult
    ) {
        accountValidator.validate(fundTransferRequest, bindingResult);
        return ResponseEntity.ok(
                bankingService.transferMoneyAndRecordTransaction(fundTransferRequest)
        );
    }


}
