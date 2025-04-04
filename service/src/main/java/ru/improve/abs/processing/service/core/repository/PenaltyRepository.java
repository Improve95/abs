package ru.improve.abs.processing.service.core.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.improve.abs.processing.service.model.credit.Credit;
import ru.improve.abs.processing.service.model.penalty.Penalty;
import ru.improve.abs.processing.service.model.penalty.PenaltyStatus;
import ru.improve.abs.processing.service.model.penalty.PenaltyType;

public interface PenaltyRepository extends
        PagingAndSortingRepository<Penalty, Long>,
        JpaRepository<Penalty, Long> {

    Page<Penalty> findAllByCreditAndTypeAndStatus(
            Credit credit,
            PenaltyType type,
            PenaltyStatus penaltyStatus,
            Pageable page
    );
}
