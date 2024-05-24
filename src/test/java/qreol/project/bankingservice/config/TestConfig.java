package qreol.project.bankingservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import qreol.project.bankingservice.repository.AccountRepository;
import qreol.project.bankingservice.service.AccountService;
import qreol.project.bankingservice.service.BankingService;
import qreol.project.bankingservice.service.InterestService;
import qreol.project.bankingservice.service.TransactionService;
import qreol.project.bankingservice.service.impl.AccountServiceImpl;
import qreol.project.bankingservice.service.impl.BankingServiceImpl;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    private final AccountRepository accountRepository;
    private final InterestService interestService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Bean
    @Primary
    public AccountService accountService() {
        return new AccountServiceImpl(accountRepository, interestService);
    }

    @Bean
    @Primary
    public BankingService bankingService() {
        return new BankingServiceImpl(accountRepository, accountService, transactionService);
    }

}
