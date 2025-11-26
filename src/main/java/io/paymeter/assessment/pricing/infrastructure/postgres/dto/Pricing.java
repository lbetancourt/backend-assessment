package io.paymeter.assessment.pricing.infrastructure.postgres.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pricing")
public class Pricing {
    @Id
    @Column(name = "id")
    private String parkingId;
    @Column(name = "date_from")
    private LocalDateTime from;
    @Column(name = "date_to")
    private LocalDateTime to;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
}
