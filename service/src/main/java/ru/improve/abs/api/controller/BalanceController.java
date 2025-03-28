package ru.improve.abs.api.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.abs.api.controller.spec.BalanceControllerSpec;
import ru.improve.abs.core.service.BalanceService;

import static ru.improve.abs.api.ApiPaths.BALANCES;

@RequiredArgsConstructor
@RestController
@RequestMapping(BALANCES)
public class BalanceController implements BalanceControllerSpec {

    private final BalanceService balanceService;

    @PostMapping("/next_days/{number}")
    public void calcNextDays(@PathVariable(name = "number") @Positive int dayNumber) {
        balanceService.calcCreditBalances(dayNumber);
    }
}
