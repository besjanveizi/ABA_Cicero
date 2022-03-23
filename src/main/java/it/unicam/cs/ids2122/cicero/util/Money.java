package it.unicam.cs.ids2122.cicero.util;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    Currency valuta;
    BigDecimal valore;

    public Money(Currency valuta, BigDecimal valore) {
        this.valuta = valuta;
        this.valore = valore;
    }
}
