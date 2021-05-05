package com.gowoon;

import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyTest {
    @Test
    public void toEuros() {
        ExchangeRate exchangeRate = createMock(ExchangeRate.class);
        expect(exchangeRate.getRate("KRW", "EUR")).andReturn(0.0007692);
        replay(exchangeRate);
        assertEquals(new Currency(7.692, "EUR"),new Currency(10000, "KRW").toEuros(exchangeRate));
    }
}