package io.paymeter.assessment.pricing;

import io.paymeter.assessment.pricing.domain.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testCurrencyIsAlwaysEuro() {
        var money = new Money(123);

        assertEquals("EUR", money.getCurrencyCode());
    }

    @Test
    void testLocalDateTimeConvertISO8601(){
        String date = "2024-02-27T09:28:13";

        var resultDate = LocalDateTime.parse(date);

        assertEquals(2024, resultDate.getYear());
        assertEquals(2, resultDate.getMonth().getValue());
        assertEquals(27, resultDate.getDayOfMonth());
        assertEquals(9, resultDate.getHour());
        assertEquals(28, resultDate.getMinute());
        assertEquals(13, resultDate.getSecond());
    }
}
