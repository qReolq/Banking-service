package qreol.project.bankingservice.service;

import qreol.project.bankingservice.domain.Transaction;
import qreol.project.bankingservice.web.dto.FundTransferRequest;

public interface BankingService {

    Transaction transferMoneyAndRecordTransaction(FundTransferRequest fundTransferRequest);

}
