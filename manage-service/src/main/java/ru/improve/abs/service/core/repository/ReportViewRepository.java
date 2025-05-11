package ru.improve.abs.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.abs.service.model.view.CreditsProfitReport;

public interface ReportViewRepository extends JpaRepository<CreditsProfitReport, Long> {

}
