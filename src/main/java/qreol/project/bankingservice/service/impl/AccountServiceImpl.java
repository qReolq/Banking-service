package qreol.project.bankingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.domain.exception.InsufficientFundsException;
import qreol.project.bankingservice.repository.AccountRepository;
import qreol.project.bankingservice.service.AccountService;
import qreol.project.bankingservice.service.InterestService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final InterestService interestService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (fromAccount.getBalance().compareTo(amount) < 0)
            throw new InsufficientFundsException();

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Override
    @Scheduled(fixedRate = 60000)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void applyInterest() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            interestService.applyInterestToAccount(account);
        }

    }

}
