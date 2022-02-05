package model.ruoli;


import model.Esperienza;

import java.util.List;

/**
 * Rappresenta un esperto e conoscitore del territorio che si propone di
 * assistere i turisti nell’<code>Esperienza</code> di visita di una città o di una escursione.
 */
public interface Cicerone {

    /**
     * Permette di creare un'<code>Esperienza</code>.
     */
    void creaEsperienza();

    /**
     * Restituisce tutte le esperienze create dal <code>Cicerone</code>.
     * @return una collezione delle esperienze create dal <code>Cicerone</code>.
     */
    List<Esperienza> getAllEsperienze();
}