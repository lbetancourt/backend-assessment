package io.paymeter.assessment.parking.infrastructure.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class CalculationResponse {
    String parkingId;
    String from;
    String to;
    Long duration;
    String price;
}
