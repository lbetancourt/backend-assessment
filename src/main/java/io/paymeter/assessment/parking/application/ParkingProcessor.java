package io.paymeter.assessment.parking.application;

import io.paymeter.assessment.parking.domain.Parking;

public interface ParkingProcessor {
    Parking calculate(Parking parking);
}
