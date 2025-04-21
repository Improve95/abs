package ru.improve.abs.info.service.core.service;

import ru.improve.abs.info.service.api.dto.credit.CreditRequest;
import ru.improve.abs.info.service.api.dto.credit.CreditResponse;

import java.util.List;

public interface CreditService {

    List<CreditResponse> getCredits(CreditRequest creditRequest);
}
