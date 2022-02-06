package model.ruoli;

import model.esperienza.SimpleEsperienza;

public interface Utente {
    void showEsperienza(SimpleEsperienza esperienza);
    void cercaEsperienze();
    void showAllEsperienze();
}
