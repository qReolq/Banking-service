package qreol.project.bankingservice.service;

import qreol.project.bankingservice.domain.Account;

import java.math.BigDecimal;

public interface AccountService {

    void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount);

    void applyInterest();

}
