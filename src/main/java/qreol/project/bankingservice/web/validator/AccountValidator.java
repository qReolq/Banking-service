package qreol.project.bankingservice.web.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import qreol.project.bankingservice.web.dto.FundTransferRequest;

@Component
@RequiredArgsConstructor
public class AccountValidator implements Validator {

    private final ErrorsChecker errorsChecker;

    @Override
    public boolean supports(Class<?> clazz) {
        return FundTransferRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        errorsChecker.checkError(errors);

        FundTransferRequest fundTransferRequest = (FundTransferRequest) target;
        if (fundTransferRequest.getFromAccountId().equals(fundTransferRequest.getToAccountId())) {
            errors.rejectValue(
                    "fromAccountId",
                    "",
                    "fromAccountId and toAccountId must not be the same"
            );
            errors.rejectValue(
                    "toAccountId",
                    "",
                    "toAccountId and fromAccountId must not be the same"
            );

        }
        errorsChecker.checkError(errors);
    }
}
