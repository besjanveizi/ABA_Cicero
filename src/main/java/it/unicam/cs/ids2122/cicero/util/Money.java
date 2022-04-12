package it.unicam.cs.ids2122.cicero.util;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.StringTokenizer;

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
    public Money(BigDecimal valore, String currency){
        this.valore = valore;
        this.valuta = Currency.getInstance(currency);
    }

    public Money(BigDecimal valore, Currency valuta) {
        this.valore = valore;
        this.valuta = valuta;
    }

    public Currency getValuta() {
        return valuta;
    }

    public BigDecimal getValore() {
        return valore;
    }

    public  BigDecimal op_multi(String posti){
        BigDecimal bigDecimal = new BigDecimal(posti);
        return this.valore.multiply(bigDecimal);
    }

    @Override
    public String toString() {
        return valore + valuta.getSymbol();
    }
}
