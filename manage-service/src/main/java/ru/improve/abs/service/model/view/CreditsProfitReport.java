package ru.improve.abs.service.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "accumulative_profit_report_view")
@Immutable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditsProfitReport {

    @Id
    private long id;

    private double profit;

    @Column(name = "date_t")
    private String recordDateText;
}