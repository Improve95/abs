package ru.improve.abs.service.core.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.improve.abs.service.model.credit.Credit;
import ru.improve.abs.service.model.credit.CreditStatus;

public interface CreditRepository extends
        JpaRepository<Credit, Long>,
        JpaSpecificationExecutor<Credit> {

    Page<Credit> findAllByCreditStatus(CreditStatus creditStatus, Pageable page);
}
