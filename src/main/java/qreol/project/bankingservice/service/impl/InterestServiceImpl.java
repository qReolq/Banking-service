package qreol.project.bankingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.repository.AccountRepository;
import qreol.project.bankingservice.service.InterestService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestServiceImpl implements InterestService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void applyInterestToAccount(Account account) {
        BigDecimal initialDeposit = account.getInitialDeposit();
        BigDecimal maxBalance = initialDeposit.multiply(new BigDecimal("2.07"));
        BigDecimal newBalance = account.getBalance().multiply(new BigDecimal("1.05"));
        if (newBalance.compareTo(maxBalance) > 0) {
            newBalance = maxBalance;
        }
        account.setBalance(newBalance);
        accountRepository.save(account);
    }


}
