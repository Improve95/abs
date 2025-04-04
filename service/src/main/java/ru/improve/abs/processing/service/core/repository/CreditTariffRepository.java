package ru.improve.abs.processing.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.abs.processing.service.model.CreditTariff;

import java.util.List;

public interface CreditTariffRepository extends JpaRepository<CreditTariff, Integer> {

    List<CreditTariff> findAll();
}
