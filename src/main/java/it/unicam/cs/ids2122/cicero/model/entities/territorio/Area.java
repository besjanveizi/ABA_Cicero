package it.unicam.cs.ids2122.cicero.model.entities.territorio;

/**
 * Rappresenta un'{@code Area} di un territorio.
 */
public interface Area {
    /**
     * Recupera l'id dell'{@code Area}.
     * @return intero dell'id.
     */
    int getId();

    /**
     * Recupera il toponimo dell'{@code Area}.
     * @return stringa del toponimo.
     */
    String getToponimo();

    /**
     * Recupera la descrizioen dell'{@code Area}.
     * @return stringa della descrizione.
     */
    String getDescrizione();
}
