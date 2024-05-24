package qreol.project.bankingservice.service;

import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.domain.Transaction;

import java.math.BigDecimal;

public interface TransactionService {

    Transaction createTransaction(Account fromAccount, Account toAccount, BigDecimal amount);

}
