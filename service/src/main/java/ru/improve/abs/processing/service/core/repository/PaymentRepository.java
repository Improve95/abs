package ru.improve.abs.processing.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.improve.abs.processing.service.model.Payment;
import ru.improve.abs.processing.service.model.credit.Credit;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface PaymentRepository extends
        PagingAndSortingRepository<Payment, Long>,
        JpaRepository<Payment, Long> {

    List<Payment> findAllByCreditAndCreatedAtBetween(Credit credit, Instant t1, Instant t2);

    @Query("select sum(p.amount) from Payment p where p.credit = :credit and p.createdAt between :t1 and :t2")
    BigDecimal sumAllByCreditAndCreatedAtBetween(
            @Param("credit") Credit credit,
            @Param("t1") Instant t1,
            @Param("t2") Instant t2
    );
}
