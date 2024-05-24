package qreol.project.bankingservice.service;

import qreol.project.bankingservice.domain.Account;

public interface InterestService {

    void applyInterestToAccount(Account account);

}
