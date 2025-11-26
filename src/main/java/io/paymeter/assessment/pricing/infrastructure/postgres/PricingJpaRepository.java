package io.paymeter.assessment.pricing.infrastructure.postgres;

import io.paymeter.assessment.pricing.infrastructure.postgres.dto.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingJpaRepository extends JpaRepository<Pricing, String> {
}
