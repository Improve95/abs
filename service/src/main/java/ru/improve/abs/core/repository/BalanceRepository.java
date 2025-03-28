package ru.improve.abs.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.improve.abs.model.Balance;
import ru.improve.abs.model.credit.Credit;

public interface BalanceRepository extends
        PagingAndSortingRepository<Balance, Long>,
        JpaRepository<Balance, Long> {

    Page<Balance> findByCredit(Credit credit, Pageable page);
}
