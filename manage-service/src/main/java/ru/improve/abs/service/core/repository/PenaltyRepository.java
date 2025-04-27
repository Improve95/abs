package ru.improve.abs.service.core.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.improve.abs.service.model.credit.Credit;
import ru.improve.abs.service.model.penalty.Penalty;
import ru.improve.abs.service.model.penalty.PenaltyStatus;
import ru.improve.abs.service.model.penalty.PenaltyType;

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
