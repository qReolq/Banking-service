package qreol.project.bankingservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import qreol.project.bankingservice.domain.User;
import qreol.project.bankingservice.web.dto.UpdateEmailRequest;
import qreol.project.bankingservice.web.dto.UpdatePhoneNumberRequest;

import java.time.LocalDate;

public interface UserService {

    User getById(Long id);

    User getByLogin(String login);

    User getByEmail(String email);

    User getByPhoneNumber(String phoneNumber);

    User save(User user);

    User updateEmail(String login, UpdateEmailRequest emailRequest);

    User updatePhoneNumber(String login, UpdatePhoneNumberRequest phoneNumberRequest);

    User removeEmail(String login, String email);

    User removePhoneNumber(String login, String phoneNumber);

    Page<User> searchUsers(String fullName, String phoneNumber, String email, LocalDate dateOfBirth, Pageable pageable);

}
