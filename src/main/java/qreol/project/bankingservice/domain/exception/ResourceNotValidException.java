package qreol.project.bankingservice.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResourceNotValidException extends RuntimeException {

    private List<FieldError> errors;

}
