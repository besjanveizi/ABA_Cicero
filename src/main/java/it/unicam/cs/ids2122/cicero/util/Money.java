package it.unicam.cs.ids2122.cicero.util;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Rappresenta la classe modello per rappresentare la quantit&agrave di un prezzo la cui valuta è impostata a {@code Locale.ITALY}.
 */
public class Money {
    Currency valuta;
    BigDecimal valore;

    public Money(BigDecimal valore) {
        this.valuta = Currency.getInstance(Locale.ITALY);
        this.valore = valore;
    }

    public Currency getValuta() {
        return valuta;
    }

    public BigDecimal getValore() {
        return valore;
    }

    @Override
    public String toString() {
        return valore + valuta.getSymbol();
    }
}
