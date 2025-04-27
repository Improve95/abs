package ru.improve.abs.service.model.penalty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.improve.abs.service.model.credit.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "penalties")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Penalty {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Credit credit;

    @Enumerated(value = EnumType.STRING)
    private PenaltyType type;

    private BigDecimal amount;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private PenaltyStatus status = PenaltyStatus.NOT_PAID;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();
}
