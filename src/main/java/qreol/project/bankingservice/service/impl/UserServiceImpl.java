package qreol.project.bankingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qreol.project.bankingservice.domain.User;
import qreol.project.bankingservice.domain.exception.ResourceNotFoundException;
import qreol.project.bankingservice.repository.UserRepository;
import qreol.project.bankingservice.repository.specification.UserSpecifications;
import qreol.project.bankingservice.service.UserService;
import qreol.project.bankingservice.web.dto.UpdateEmailRequest;
import qreol.project.bankingservice.web.dto.UpdatePhoneNumberRequest;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User save(User user) {
        enrichUser(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateEmail(String login, UpdateEmailRequest emailRequest) {
        User user = getByLogin(login);

        if (emailRequest.getOldEmail() != null)
            user.getEmails().remove(emailRequest.getOldEmail());

        user.getEmails().add(emailRequest.getNewEmail());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updatePhoneNumber(String login, UpdatePhoneNumberRequest phoneNumberRequest) {
        User user = getByLogin(login);

        if (phoneNumberRequest.getOldPhoneNumber() != null)
            user.getPhoneNumbers().remove(phoneNumberRequest.getOldPhoneNumber());

        user.getPhoneNumbers().add(phoneNumberRequest.getNewPhoneNumber());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User removeEmail(String login, String email) {
        User user = getByLogin(login);

        if (!user.getEmails().contains(email))
            throw new ResourceNotFoundException("Email is not found");

        if (user.getEmails().size() == 1)
            throw new IllegalArgumentException("Can't delete last email");

        user.getEmails().remove(email);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User removePhoneNumber(String login, String phoneNumber) {
        User user = getByLogin(login);

        if (!user.getPhoneNumbers().contains(phoneNumber))
            throw new ResourceNotFoundException("Phone number is not found");

        if (user.getPhoneNumbers().size() == 1)
            throw new IllegalArgumentException("Can't delete last phone number");

        user.getPhoneNumbers().remove(phoneNumber);

        return userRepository.save(user);
    }

    @Override
    public Page<User> searchUsers(String fullName, String phoneNumber, String email, LocalDate dateOfBirth, Pageable pageable) {
        Specification<User> spec = Specification.where(null);

        if (fullName != null)
            spec = spec.and(UserSpecifications.fullNameLike(fullName));

        if (phoneNumber != null)
            spec = spec.and(UserSpecifications.hasPhoneNumber(phoneNumber));

        if (email != null)
            spec = spec.and(UserSpecifications.hasEmail(email));

        if (dateOfBirth != null)
            spec = spec.and(UserSpecifications.dateOfBirthAfter(dateOfBirth));

        return userRepository.findAll(spec, pageable);
    }

    private void enrichUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
