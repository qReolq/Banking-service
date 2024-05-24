package qreol.project.bankingservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import qreol.project.bankingservice.domain.exception.AccessDeniedException;
import qreol.project.bankingservice.domain.exception.InsufficientFundsException;
import qreol.project.bankingservice.domain.exception.ResourceNotFoundException;
import qreol.project.bankingservice.domain.exception.ResourceNotValidException;
import qreol.project.bankingservice.web.dto.ExceptionBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceIllegalState(IllegalStateException e) {
        return new ExceptionBody(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("Access denied", LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed", LocalDateTime.now());
        exceptionBody.setErrors(mapListToMap(e.getBindingResult().getFieldErrors()));

        return exceptionBody;
    }

    @ExceptionHandler(ResourceNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(ResourceNotValidException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed", LocalDateTime.now());
        exceptionBody.setErrors(mapListToMap(e.getErrors()));

        return exceptionBody;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleBadCredentials(BadCredentialsException e) {
        return new ExceptionBody("Invalid login or password", LocalDateTime.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalArgument(IllegalArgumentException e) {
        return new ExceptionBody(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleInsufficientFunds(InsufficientFundsException e) {
        return new ExceptionBody("Insufficient funds", LocalDateTime.now());
    }

    private Map<String, String> mapListToMap(List<FieldError> errors) {
        return errors
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

}
