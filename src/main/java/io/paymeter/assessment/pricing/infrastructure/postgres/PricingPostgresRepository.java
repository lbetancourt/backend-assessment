package io.paymeter.assessment.pricing.infrastructure.postgres;

import io.paymeter.assessment.pricing.domain.PricingRepository;
import io.paymeter.assessment.pricing.infrastructure.postgres.dto.Pricing;
import io.paymeter.assessment.pricing.infrastructure.postgres.exception.PricingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PricingPostgresRepository implements PricingRepository {

    private final PricingJpaRepository pricingJpaRepository;

    @Override
    public Pricing get(String parkingId) {
        if (pricingJpaRepository.findById(parkingId).isPresent()) {
            return pricingJpaRepository.findById(parkingId).get();
        } else {
            throw new PricingNotFoundException(String.format("NOT FOUND Pricing %s", parkingId));
        }
    }
}
