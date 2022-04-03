package it.unicam.cs.ids2122.cicero.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    private static Money m;
    @BeforeAll
    static void setUp() {
        m =  new Money(new BigDecimal("435.78"));
    }

    @Test
    void shouldBe_EUR() {
        assertEquals(m.getValuta().getCurrencyCode(), "EUR");
    }

    @Test
    void shouldBe_sameValue() {
        assertEquals(m.getValore(), new BigDecimal("435.78"));
    }

    @Test
    void testToString() {
        assertEquals(m.toString(), "435.78€");
    }
}