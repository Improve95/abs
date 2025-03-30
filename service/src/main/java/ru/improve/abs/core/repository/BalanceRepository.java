package ru.improve.abs.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.improve.abs.model.Balance;
import ru.improve.abs.model.credit.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BalanceRepository extends
        PagingAndSortingRepository<Balance, Long>,
        JpaRepository<Balance, Long> {

    Page<Balance> findByCredit(Credit credit, Pageable page);

    @Query("select sum(b.accruedByPercent) from Balance b where b.credit = :credit and b.createdAt between :d1 and :d2")
    BigDecimal sumAccruedByPercentByCreditAndCreatedAtBetween(
            @Param("credit") Credit credit,
            @Param("d1") LocalDate d1,
            @Param("d2") LocalDate d2
    );
}
