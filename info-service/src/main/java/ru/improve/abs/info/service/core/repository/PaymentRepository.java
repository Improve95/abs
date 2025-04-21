package ru.improve.abs.info.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.improve.abs.info.service.model.Payment;

@Repository
public interface PaymentRepository extends
        JpaRepository<Payment, Long>,
        JpaSpecificationExecutor<Payment> {


}
