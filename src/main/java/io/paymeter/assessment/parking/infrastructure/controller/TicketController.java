package io.paymeter.assessment.parking.infrastructure.controller;

import io.paymeter.assessment.parking.application.ParkingProcessor;
import io.paymeter.assessment.parking.domain.Parking;
import io.paymeter.assessment.parking.infrastructure.controller.dto.CalculationRequest;
import io.paymeter.assessment.parking.infrastructure.controller.dto.CalculationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {

    private final ParkingProcessor parkingProcessor;

    @PostMapping("/calculate")
    public CalculationResponse calculate(@RequestBody CalculationRequest request) {
        return convertTo(parkingProcessor.calculate(convertTo(request)));
    }

    private Parking convertTo(CalculationRequest request) {
        return Parking.builder()
                .withParkingId(request.getParkingId())
                .withFrom(request.getFrom())
                .withTo(request.getTo())
                .build();
    }

    private CalculationResponse convertTo(Parking parking) {
        return CalculationResponse.builder()
                .withParkingId(parking.getParkingId())
                .withFrom(parking.getFrom())
                .withTo(parking.getTo())
                .withDuration(parking.getDuration())
                .withPrice(parking.getPrice())
                .build();
    }
}
