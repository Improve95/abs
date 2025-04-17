package ru.improve.abs.info.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.improve.abs.info.service.model.credit.Credit;

@Repository
public interface CreditRepository extends
        JpaRepository<Credit, Long>,
        JpaSpecificationExecutor<Credit> {

}
