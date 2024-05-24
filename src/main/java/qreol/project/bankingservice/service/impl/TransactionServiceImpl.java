package qreol.project.bankingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.domain.Transaction;
import qreol.project.bankingservice.repository.TransactionRepository;
import qreol.project.bankingservice.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction createTransaction(Account fromAccount, Account toAccount, BigDecimal amount) {
        Transaction transaction = new Transaction(
                fromAccount,
                toAccount,
                amount,
                LocalDateTime.now()
        );

        return transactionRepository.save(transaction);
    }
}




