package ru.improve.abs.service.core.repository.request;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import ru.improve.abs.service.api.dto.credit.GetExpiredCreditsReport;
import ru.improve.abs.service.api.dto.credit.GetLoansAmountOutputResponse;
import ru.improve.abs.service.api.dto.payment.GetTotalPaymentsResponse;
import ru.improve.abs.service.model.Payment;
import ru.improve.abs.service.model.credit.Credit;
import ru.improve.abs.service.model.penalty.Penalty;

import java.time.LocalDate;

import static ru.improve.abs.service.model.penalty.PenaltyStatus.NOT_PAID;

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
        return criteriaQuery.multiselect(
                cb.count(creditRoot.get("initialAmount")),
                cb.sum(creditRoot.get("initialAmount")),
                cb.avg(creditRoot.get("initialAmount"))
        ).where(cb.between(creditRoot.get("takingDate"), from, to));
    }

    public static CriteriaQuery<GetTotalPaymentsResponse> getTotalPaymentsResponseRequest(
            EntityManager em,
            LocalDate from,
            LocalDate to
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GetTotalPaymentsResponse> criteriaQuery = cb.createQuery(
                GetTotalPaymentsResponse.class
        );
        Root<Payment> paymentRoot = criteriaQuery.from(Payment.class);
        return criteriaQuery.multiselect(cb.sum(paymentRoot.get("amount")))
                .where(cb.between(paymentRoot.get("createdAt"), from, to));
    }

    public static CriteriaQuery<GetExpiredCreditsReport> getExpiredCreditsReportRequest(
            EntityManager em,
            LocalDate closeDateFrom,
            LocalDate closeDateTo
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GetExpiredCreditsReport> criteriaQuery = cb.createQuery(GetExpiredCreditsReport.class);
        Root<Credit> creditRoot = criteriaQuery.from(Credit.class);
        Join<Credit, Penalty> creditPenaltyJoin = creditRoot.join("penalties", JoinType.LEFT);
        return criteriaQuery.multiselect(
                cb.count(creditRoot),
                cb.count(cb.equal(creditPenaltyJoin.get("status"), NOT_PAID))
        ).where(cb.between(creditRoot.get("takingDate"), closeDateFrom, closeDateTo));
    }
}
