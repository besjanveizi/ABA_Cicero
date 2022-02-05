package model;

import model.esperienza.Esperienza;

import java.util.List;

/**
 * Rappresenta un raccoglitore di tutte le esperienze pubbliche nella piattaforma Cicero.
 */
public interface Bacheca {

    /**
     * Restituisce tutte le esperienze pubbliche nella piattaforma.
     * @return lista di tutte le esperienze pubblicate.
     */
    List<Esperienza> showAllEsperienze();

    /**
     * Restituisce l'<code>Esperienza</code> cui identificativo corrisponde a quello dato.
     * @param id identificativo dell'<code>Esperienza</code> che si vuole ottenere.
     * @return <code>Esperienza</code> corrispondente all'identificativo dato.
     */
    Esperienza showEsperienza(int id);
}