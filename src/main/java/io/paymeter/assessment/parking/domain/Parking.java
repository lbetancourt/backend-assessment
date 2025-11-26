package io.paymeter.assessment.parking.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class Parking {
    String parkingId;
    String from;
    String to;
    Long duration;
    String price;
}
