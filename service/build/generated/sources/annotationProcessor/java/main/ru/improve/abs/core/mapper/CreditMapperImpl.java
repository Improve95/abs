package ru.improve.abs.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.improve.abs.api.dto.credit.CreditRequestResponse;
import ru.improve.abs.api.dto.credit.CreditResponse;
import ru.improve.abs.api.dto.credit.CreditTariffResponse;
import ru.improve.abs.api.dto.credit.PostCreditRequest;
import ru.improve.abs.api.dto.credit.PostCreditRequestRequest;
import ru.improve.abs.model.CreditRequest;
import ru.improve.abs.model.CreditTariff;
import ru.improve.abs.model.credit.Credit;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-25T01:03:40+0700",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class CreditMapperImpl implements CreditMapper {

    @Override
    public CreditRequest toCreditRequest(PostCreditRequestRequest creditRequestRequest) {
        if ( creditRequestRequest == null ) {
            return null;
        }

        CreditRequest.CreditRequestBuilder creditRequest = CreditRequest.builder();

        creditRequest.creditAmount( creditRequestRequest.getCreditAmount() );
        creditRequest.creditDuration( (int) creditRequestRequest.getCreditDuration() );

        return creditRequest.build();
    }

    @Override
    public CreditTariffResponse toCreditTariffResponse(CreditTariff creditTariff) {
        if ( creditTariff == null ) {
            return null;
        }

        CreditTariffResponse.CreditTariffResponseBuilder creditTariffResponse = CreditTariffResponse.builder();

        creditTariffResponse.id( creditTariff.getId() );
        creditTariffResponse.type( creditTariff.getType() );
        creditTariffResponse.upToAmount( creditTariff.getUpToAmount() );
        creditTariffResponse.creditPercent( creditTariff.getCreditPercent() );

        return creditTariffResponse.build();
    }

    @Override
    public CreditRequestResponse toCreditRequestResponse(CreditRequest creditRequest) {
        if ( creditRequest == null ) {
            return null;
        }

        CreditRequestResponse.CreditRequestResponseBuilder creditRequestResponse = CreditRequestResponse.builder();

        creditRequestResponse.id( creditRequest.getId() );
        creditRequestResponse.createdAt( creditRequest.getCreatedAt() );

        creditRequestResponse.creditTariffId( creditRequest.getCreditTariff().getId() );
        creditRequestResponse.userId( creditRequest.getUser().getId() );

        return creditRequestResponse.build();
    }

    @Override
    public Credit toCredit(PostCreditRequest creditRequest) {
        if ( creditRequest == null ) {
            return null;
        }

        Credit.CreditBuilder credit = Credit.builder();

        credit.initialAmount( creditRequest.getInitialAmount() );
        credit.percent( creditRequest.getPercent() );
        credit.creditPeriod( creditRequest.getCreditPeriod() );
        credit.monthAmount( creditRequest.getMonthAmount() );

        return credit.build();
    }

    @Override
    public CreditResponse toCreditResponse(Credit credit) {
        if ( credit == null ) {
            return null;
        }

        CreditResponse.CreditResponseBuilder creditResponse = CreditResponse.builder();

        creditResponse.id( credit.getId() );
        creditResponse.initialAmount( credit.getInitialAmount() );
        creditResponse.takingDate( credit.getTakingDate() );
        creditResponse.percent( credit.getPercent() );
        creditResponse.creditPeriod( credit.getCreditPeriod() );
        creditResponse.monthAmount( credit.getMonthAmount() );
        creditResponse.creditStatus( credit.getCreditStatus() );

        creditResponse.creditTariffId( credit.getCreditTariff().getId() );
        creditResponse.userId( credit.getUser().getId() );

        return creditResponse.build();
    }
}
