package io.paymeter.assessment.parking.application;

import io.paymeter.assessment.parking.domain.InvalidDateRangeException;
import io.paymeter.assessment.parking.domain.Parking;
import io.paymeter.assessment.pricing.domain.PricingRepository;
import io.paymeter.assessment.pricing.infrastructure.postgres.dto.Pricing;
import io.paymeter.assessment.pricing.infrastructure.postgres.exception.PricingNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
class ParkingUseCaseTests {
    private ParkingUseCase parkingUseCase;
    @MockBean
    private PricingRepository pricingRepository;

    @BeforeEach
    void configure() {
        parkingUseCase = new ParkingUseCase(pricingRepository);
    }

    @Test
    void givenParkingZero_whenCalculate_thenPriceZero() {
        Pricing pricing = new Pricing();
        pricing.setParkingId("P000001");
        pricing.setFrom(LocalDateTime.parse("2025-11-25T06:28:13"));
        pricing.setTo(LocalDateTime.parse("2025-11-25T06:28:40"));
        pricing.setDuration(0);
        pricing.setPrice(BigDecimal.valueOf(0.00));
        Mockito.when(pricingRepository.get(Mockito.anyString())).thenReturn(pricing);
        Parking parking = Parking.builder()
                .withParkingId("P000001")
                .withFrom("2025-11-25T06:28:13")
                .withTo("2025-11-25T06:28:40")
                .build();
        var parkingResult = parkingUseCase.calculate(parking);
        Assertions.assertEquals("0EUR", parkingResult.getPrice());
    }

    @Test
    void givenParkingHours_whenCalculate_thenPrice() {
        Pricing pricing = new Pricing();
        pricing.setParkingId("P000002");
        pricing.setFrom(LocalDateTime.parse("2025-11-25T06:28:13"));
        pricing.setTo(LocalDateTime.parse("2025-11-25T08:28:40"));
        pricing.setDuration(0);
        pricing.setPrice(BigDecimal.valueOf(0.00));
        Mockito.when(pricingRepository.get(Mockito.anyString())).thenReturn(pricing);
        Parking parking = Parking.builder()
                .withParkingId("P000002")
                .withFrom("2025-11-25T06:28:13")
                .withTo("2025-11-25T08:28:40")
                .build();
        var parkingResult = parkingUseCase.calculate(parking);
        Assertions.assertEquals("600EUR", parkingResult.getPrice());
    }

    @Test
    void givenParking_whenCalculate_thenError() {
        Parking parking = Parking.builder()
                .withParkingId("P000003")
                .withFrom("2025-11-25T10:28:13")
                .withTo("2025-11-25T08:28:40")
                .build();
        Assertions.assertThrows(InvalidDateRangeException.class, () -> parkingUseCase.calculate(parking));
    }

    @Test
    void givenParking_whenCalculate_thenNotFound() {
        Parking parking = Parking.builder()
                .withParkingId("P000004")
                .withFrom("2025-11-25T08:28:13")
                .withTo("2025-11-25T09:40:40")
                .build();
        Assertions.assertThrows(PricingNotFoundException.class, () -> parkingUseCase.calculate(parking));
    }
}
