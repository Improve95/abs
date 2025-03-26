package ru.improve.abs.core.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.improve.abs.core.repository.CreditRepository;

@RequiredArgsConstructor
@Service
public class ProcessingCreditServiceImp {

    private CreditRepository creditRepository;

    public void calcCreditBalances(int dayNumber) {

    }
}
