package qreol.project.bankingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qreol.project.bankingservice.domain.Account;
import qreol.project.bankingservice.domain.Transaction;
import qreol.project.bankingservice.repository.AccountRepository;
import qreol.project.bankingservice.service.AccountService;
import qreol.project.bankingservice.service.BankingService;
import qreol.project.bankingservice.service.TransactionService;
import qreol.project.bankingservice.web.dto.FundTransferRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankingServiceImpl implements BankingService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public Transaction transferMoneyAndRecordTransaction(FundTransferRequest fundTransferRequest) {
        Account fromAccount = accountRepository.findAccountByUserId(fundTransferRequest.getFromAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account toAccount = accountRepository.findAccountByUserId(fundTransferRequest.getToAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        accountService.transferMoney(fromAccount, toAccount, fundTransferRequest.getAmount());
        return transactionService.createTransaction(fromAccount, toAccount, fundTransferRequest.getAmount());
    }

}
