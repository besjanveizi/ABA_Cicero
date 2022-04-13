package it.unicam.cs.ids2122.cicero.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    private static Money m;
    @BeforeAll
    static void setUp() {
        m =  new Money(new BigDecimal("435.80"));
    }

    @Test
    void shouldBe_EUR() {
        assertEquals(m.getValuta().getCurrencyCode(), "EUR");
    }

    @Test
    void shouldBe_sameValue() {
        assertEquals(m.getValore(), new BigDecimal("435.80"));
    }

    @Test
    void testToString_singleDecimal() {
        assertEquals(m.toString(), "435.80€");
    }

    @Test
    void testToString_doubleDecimal() {
        assertEquals(new Money(new BigDecimal("435.08")).toString(), "435.08€");
    }

    @Test
    void op_multi() {
        assertEquals(m.op_multi("4").toString(), "1743.20");
        assertEquals(new Money(new BigDecimal("435.08")).op_multi("4").toString(), "1740.32");
    }
}