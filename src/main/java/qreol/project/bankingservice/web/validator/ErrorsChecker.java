package qreol.project.bankingservice.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import qreol.project.bankingservice.domain.exception.ResourceNotValidException;

@Component
public class ErrorsChecker {

    public void checkError(Errors errors) {
        if (errors.hasErrors()) {
            throw new ResourceNotValidException(errors.getFieldErrors());
        }
    }

}
