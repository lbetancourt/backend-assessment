package io.paymeter.assessment.pricing.domain;

import java.util.Currency;
import java.util.Objects;

public class Money {
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");

    private final long amount;
    private final Currency currency;

    public Money(int amount) {
        this.amount = amount;
        this.currency = DEFAULT_CURRENCY;
    }

    public Money(long amount) {
        this.amount = amount;
        this.currency = DEFAULT_CURRENCY;
    }

    public long getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    @Override
    public String toString() {
        return String.format("%d%s", Math.round(amount * 100), getCurrencyCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
