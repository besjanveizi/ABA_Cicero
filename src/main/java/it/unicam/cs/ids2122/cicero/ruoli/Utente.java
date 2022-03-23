package it.unicam.cs.ids2122.cicero.ruoli;

import it.unicam.cs.ids2122.cicero.model.esperienza.SimpleEsperienza;

public interface Utente {
    void showEsperienza(SimpleEsperienza esperienza);
    void cercaEsperienze();
    void showAllEsperienze();
}
