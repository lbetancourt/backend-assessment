package io.paymeter.assessment.pricing.infrastructure.postgres.exception;

public class PricingNotFoundException extends RuntimeException{
    public PricingNotFoundException(String message){super(message);}
}
