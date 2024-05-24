package qreol.project.bankingservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import qreol.project.bankingservice.config.TestConfig;
import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.domain.Transaction;
import qreol.project.bankingservice.repository.AccountRepository;
import qreol.project.bankingservice.service.AccountService;
import qreol.project.bankingservice.service.BankingService;
import qreol.project.bankingservice.service.TransactionService;
import qreol.project.bankingservice.web.dto.FundTransferRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@SpringBootTest
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class BankingServiceImplTest {

    @MockBean
    private  AccountRepository accountRepository;

    @MockBean
    private  AccountService accountService;

    @MockBean
    private  TransactionService transactionService;

    @Autowired
    private BankingService bankingService;

    @Test
    public void transferMoneyAndRecordTransaction_Success() {
        FundTransferRequest fundTransferRequest = new FundTransferRequest();
        fundTransferRequest.setFromAccountId(1L);
        fundTransferRequest.setToAccountId(2L);
        fundTransferRequest.setAmount(new BigDecimal("100.00"));

        Account fromAccount = new Account();
        fromAccount.setId(1L);

        Account toAccount = new Account();
        toAccount.setId(2L);

        Transaction expectedTransaction = new Transaction();

        Mockito.when(accountRepository.findAccountByUserId(1L)).thenReturn(Optional.of(fromAccount));
        Mockito.when(accountRepository.findAccountByUserId(2L)).thenReturn(Optional.of(toAccount));

        Mockito.when(transactionService.createTransaction(any(), any(), any())).thenReturn(expectedTransaction);

        Transaction result = bankingService.transferMoneyAndRecordTransaction(fundTransferRequest);

        Assertions.assertNotNull(result);
        Mockito.verify(accountService, times(1)).transferMoney(eq(fromAccount), eq(toAccount), eq(new BigDecimal("100.00")));
        Mockito.verify(transactionService, times(1)).createTransaction(eq(fromAccount), eq(toAccount), eq(new BigDecimal("100.00")));
    }

    @Test
    public void transferMoneyAndRecordTransaction_SourceAccountNotFound() {
        FundTransferRequest fundTransferRequest = new FundTransferRequest();
        fundTransferRequest.setFromAccountId(1L);
        fundTransferRequest.setToAccountId(2L);
        fundTransferRequest.setAmount(new BigDecimal("100.00"));

        Mockito.when(accountRepository.findAccountByUserId(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> bankingService.transferMoneyAndRecordTransaction(fundTransferRequest)
        );
    }

    @Test
    public void transferMoneyAndRecordTransaction_DestinationAccountNotFound() {
        FundTransferRequest fundTransferRequest = new FundTransferRequest();
        fundTransferRequest.setFromAccountId(1L);
        fundTransferRequest.setToAccountId(2L);
        fundTransferRequest.setAmount(new BigDecimal("100.00"));

        Account fromAccount = new Account();
        fromAccount.setId(1L);

        Mockito.when(accountRepository.findAccountByUserId(1L)).thenReturn(Optional.of(fromAccount));
        Mockito.when(accountRepository.findAccountByUserId(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> bankingService.transferMoneyAndRecordTransaction(fundTransferRequest)
        );
    }

}
