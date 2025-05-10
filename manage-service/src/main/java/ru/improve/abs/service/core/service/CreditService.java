package ru.improve.abs.service.core.service;

import ru.improve.abs.service.api.dto.credit.CreditRequest;
import ru.improve.abs.service.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.service.api.dto.credit.CreditResponse;
import ru.improve.abs.service.api.dto.credit.GetLoansAmountOutputResponse;
import ru.improve.abs.service.api.dto.credit.PostCreditRequest;
import ru.improve.abs.service.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.service.model.CreditTariff;
import ru.improve.abs.service.model.credit.Credit;

import java.time.LocalDate;
import java.util.List;

public interface CreditService {

    List<CreditResponse> getCredits(CreditRequest creditRequest);

//    List<CreditTariffResponse> getAllCreditTariffs();

    CreditRequestResponse createCreditRequest(PostCreditRequestRequest postCreditRequest);

    /*List<CreditResponse> getAllCredits(int pageNumber, int pageSize);

    CreditResponse getCreditById(long creditId);

    List<CreditResponse> getAllCreditsByUserId(int userId, int pageNumber, int pageSize);*/

    GetLoansAmountOutputResponse getLoansAmountOutputReport(LocalDate from, LocalDate to);

    double getExpiredCreditPercentRatio(LocalDate from, LocalDate to);

    CreditResponse createCredit(PostCreditRequest creditRequest);

//    CreditResponse takeCreatedCredit(long creditId);

    CreditTariff findCreditTariffById(int id);

    Credit findCreditById(long creditId);
}
