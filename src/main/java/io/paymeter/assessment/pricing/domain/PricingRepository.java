package io.paymeter.assessment.pricing.domain;

import io.paymeter.assessment.pricing.infrastructure.postgres.dto.Pricing;

public interface PricingRepository {
    Pricing get(String parkingId);
}
