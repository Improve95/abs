package ru.improve.abs.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.improve.abs.service.model.credit.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "balances")
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Balance {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Credit credit;

    @Column(name = "remaining_debt")
    private BigDecimal remainingDebt;

    @Column(name = "remaining_month_debt")
    private BigDecimal remainingMonthDebt;

    @Column(name = "accrued_by_percent")
    private BigDecimal accruedByPercent;

    @Column(name = "created_at")
    private LocalDate createdAt;
}

