package qreol.project.bankingservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import qreol.project.bankingservice.config.TestConfig;
import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.domain.exception.InsufficientFundsException;
import qreol.project.bankingservice.repository.AccountRepository;
import qreol.project.bankingservice.service.impl.AccountServiceImpl;

import java.math.BigDecimal;

@SpringBootTest
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;
    @Autowired
    private AccountServiceImpl accountService;

    @Test
    public void testTransferMoneyWithInsufficientFunds() {
        Account fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal("100.00"));
        Account toAccount = new Account();
        BigDecimal amount = new BigDecimal("200.00");

        Assertions.assertThrows(
                InsufficientFundsException.class,
                () -> accountService.transferMoney(fromAccount, toAccount, amount)
        );
    }

    @Test
    public void testTransferMoneySuccessfully() {
        Account fromAccount = new Account();
        Account toAccount = new Account();
        BigDecimal amount = new BigDecimal("200.00");

        fromAccount.setBalance(new BigDecimal("500.00"));
        toAccount.setBalance(new BigDecimal("0.00"));

        accountService.transferMoney(fromAccount, toAccount, amount);

        Assertions.assertEquals(0, fromAccount.getBalance().compareTo(new BigDecimal("300.00")));
        Assertions.assertEquals(0, toAccount.getBalance().compareTo(new BigDecimal("200.00")));

    }
}
