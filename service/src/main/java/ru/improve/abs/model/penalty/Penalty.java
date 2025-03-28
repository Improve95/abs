package ru.improve.abs.model.penalty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Enumerated(value = EnumType.STRING)
    private PenaltyType type;

    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    private PenaltyStatus status;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
