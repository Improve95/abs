package ru.improve.abs.service.core.repository.request;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import ru.improve.abs.service.api.dto.credit.GetLoansAmountOutputResponse;
import ru.improve.abs.service.model.Payment;
import ru.improve.abs.service.model.credit.Credit;

import java.time.LocalDate;

public final class CriteriaApiRequests {

    public static CriteriaQuery<GetLoansAmountOutputResponse> getLoansAmountOutputReportRequest(
            EntityManager em,
            LocalDate from,
            LocalDate to
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GetLoansAmountOutputResponse> criteriaQuery = cb.createQuery(
                GetLoansAmountOutputResponse.class
        );
        Root<Credit> creditRoot = criteriaQuery.from(Credit.class);
        Root<Payment> paymentRoot = criteriaQuery.from(Payment.class);
        Join<Credit, Payment> creditPaymentJoin = creditRoot.join("payments");
        return criteriaQuery.multiselect(
                cb.count(creditPaymentJoin.get("amount")),
                cb.sum(creditPaymentJoin.get("amount")),
                cb.avg(creditPaymentJoin.get("amount"))
        ).where(cb.between(paymentRoot.get("createdAt"), from, to));
    }
}
