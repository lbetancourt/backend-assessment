package io.paymeter.assessment.parking.application;

import io.paymeter.assessment.parking.domain.InvalidDateRangeException;
import io.paymeter.assessment.parking.domain.Parking;
import io.paymeter.assessment.pricing.domain.Money;
import io.paymeter.assessment.pricing.domain.PricingRepository;
import io.paymeter.assessment.pricing.infrastructure.postgres.dto.Pricing;
import io.paymeter.assessment.pricing.infrastructure.postgres.exception.PricingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingUseCase implements ParkingProcessor {
    private static final int DEFAULT_PRICE_LESS_A_MINUTE = 0;
    private final PricingRepository pricingRepository;

    @Override
    public Parking calculate(Parking parking) {
        LocalDateTime dateFrom = LocalDateTime.parse(parking.getFrom());
        LocalDateTime dateTo = LocalDateTime.parse(parking.getTo());
        if (dateTo.isBefore(dateFrom)) {
            throw new InvalidDateRangeException();
        }
        Pricing pricing = pricingRepository.get(parking.getParkingId());
        if (pricing == null){
            throw new PricingNotFoundException(String.format("NOT FOUND Pricing %s", parking.getParkingId()));
        }
        long durationMinutes = Duration.between(dateFrom, dateTo).toMinutes();
        if (durationMinutes < 1) {
            return Parking.builder()
                    .withParkingId(parking.getParkingId())
                    .withFrom(parking.getFrom())
                    .withTo(parking.getTo())
                    .withDuration(0L)
                    .withPrice(new Money(DEFAULT_PRICE_LESS_A_MINUTE).toString())
                    .build();
        }
        long durationHours = Duration.between(dateFrom, dateTo).toHours();
        long totalPrice = durationHours * 3;
        return Parking.builder()
                .withParkingId(pricing.getParkingId())
                .withFrom(pricing.getFrom().toString())
                .withTo(pricing.getTo().toString())
                .withDuration(durationMinutes)
                .withPrice(new Money(totalPrice).toString())
                .build();
    }
}
