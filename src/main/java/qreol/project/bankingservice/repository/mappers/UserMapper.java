package qreol.project.bankingservice.repository.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.domain.User;
import qreol.project.bankingservice.web.dto.RegisterUserRequest;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper model;

    public User toEntity(RegisterUserRequest dto) {
        User user = model.map(dto, User.class);

        Account account = new Account();
        account.setUser(user);
        account.setInitialDeposit(dto.getInitialDeposit());
        account.setBalance(dto.getInitialDeposit());

        user.getEmails().add(dto.getEmail());
        user.getPhoneNumbers().add(dto.getPhoneNumber());
        user.setAccount(account);

        return user;
    }

}
