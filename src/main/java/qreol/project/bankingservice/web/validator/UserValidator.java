package qreol.project.bankingservice.web.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import qreol.project.bankingservice.domain.User;
import qreol.project.bankingservice.repository.UserRepository;
import qreol.project.bankingservice.web.dto.RegisterUserRequest;
import qreol.project.bankingservice.web.dto.UpdateEmailRequest;
import qreol.project.bankingservice.web.dto.UpdatePhoneNumberRequest;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private static final Pattern RUSSIAN_PHONE_NUMBER_PATTERN = Pattern.compile("^(\\+7|8)\\d{10}$");
    private final ErrorsChecker errorsChecker;
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterUserRequest.class.equals(clazz) ||
                UpdateEmailRequest.class.equals(clazz) ||
                UpdatePhoneNumberRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        errorsChecker.checkError(errors);
        if (target instanceof RegisterUserRequest) {
            validateRegisterUserRequest((RegisterUserRequest) target, errors);
        } else if (target instanceof UpdateEmailRequest) {
            validateEmail((UpdateEmailRequest) target, errors);
        } else if (target instanceof UpdatePhoneNumberRequest) {
            validatePhoneNumber((UpdatePhoneNumberRequest) target, errors);
        }

        errorsChecker.checkError(errors);
    }

    private void validateRegisterUserRequest(RegisterUserRequest user, Errors errors) {
        validatePhoneNumber(user.getPhoneNumber(), errors);
        validateLogin(user.getLogin(), errors);
        validateEmail(user.getEmail(), errors);
    }

    private void validateEmail(UpdateEmailRequest emailRequest, Errors errors) {
        validateEmailUniqueness(emailRequest.getNewEmail(), "newEmail", errors);
        validateEmailExistence(emailRequest.getOldEmail(), "oldEmail", errors);
    }

    private void validatePhoneNumber(UpdatePhoneNumberRequest phoneNumberRequest, Errors errors) {
        validatePhoneNumberUniqueness(phoneNumberRequest.getNewPhoneNumber(), "newPhoneNumber", errors);
        validatePhoneNumberFormat(phoneNumberRequest.getNewPhoneNumber(), "newPhoneNumber", errors);
        validatePhoneNumberExistence(phoneNumberRequest.getOldPhoneNumber(), "oldPhoneNumber", errors);
    }

    private void validatePhoneNumber(String phoneNumber, Errors errors) {
        validatePhoneNumberUniqueness(phoneNumber, "phoneNumber", errors);
        validatePhoneNumberFormat(phoneNumber, "phoneNumber", errors);
    }

    private void validateLogin(String login, Errors errors) {
        validateUniqueness(userRepository.findByLogin(login), "login", errors, "This login is already taken");
    }

    private void validateEmail(String email, Errors errors) {
        validateUniqueness(userRepository.findByEmail(email), "email", errors, "This email is already taken");
    }

    private void validateEmailUniqueness(String email, String field, Errors errors) {
        validateUniqueness(userRepository.findByEmail(email), field, errors, "This email is already taken");
    }

    private void validateEmailExistence(String email, String field, Errors errors) {
        if (email != null) {
            validateExistence(userRepository.findByEmail(email), field, errors, "Old email is not found");
        }
    }

    private void validatePhoneNumberUniqueness(String phoneNumber, String field, Errors errors) {
        validateUniqueness(userRepository.findByPhoneNumber(phoneNumber), field, errors, "This phone number is already taken");
    }

    private void validatePhoneNumberExistence(String phoneNumber, String field, Errors errors) {
        if (phoneNumber != null) {
            validateExistence(userRepository.findByPhoneNumber(phoneNumber), field, errors, "Old phone number is not found");
        }
    }

    private void validatePhoneNumberFormat(String phoneNumber, String field, Errors errors) {
        if (!RUSSIAN_PHONE_NUMBER_PATTERN.matcher("+" + phoneNumber).matches()) {
            errors.rejectValue(field, "", "Invalid phone number");
        }
    }

    private void validateUniqueness(Optional<User> found, String field, Errors errors, String message) {
        if (found.isPresent()) {
            errors.rejectValue(field, "", message);
        }
    }

    private void validateExistence(Optional<User> found, String field, Errors errors, String message) {
        if (found.isEmpty()) {
            errors.rejectValue(field, "", message);
        }
    }
}