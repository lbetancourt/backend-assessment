package io.paymeter.assessment.parking.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequest {
    private String parkingId;
    private String from;
    private String to;
}
