package it.unicam.cs.ids2122.cicero.model.prenotazione.pagamento;

/**
 * interfaccia  dedicata alla simulazione di un pagamento
 */
public interface FunFattura extends Fattura{


    /**
     * primo stadio del pagamento,
     * inserisce l' origine del pagamento, mentre la destinazione
     * è data da <code>{@link SystemConstraints}</code>
     *  * deve aggiornare lo stato di un pagamento
     *      * @param statoPagamento <code>{@link StatoPagamento}</code>
     * @param conto_origine altrimenti definito come il turista
     */
    void turista_admin(String conto_origine);

    /**
     * seconda stadio del pagamento,
     * dal conto origine del <code>{@link Utente}</code>
     *  * deve aggiornare lo stato di un pagamento
     *      * @param statoPagamento <code>{@link StatoPagamento}</code>
     * @param conto_destinatario altrimenti definito come cicerone
     */
    void admin_cicerone(String conto_destinatario);

    /**
     * eccezione del flusso di pagamento in cui invece di transitare verso
     * il <code>{@link Utente}</code>
     * che ha creato l <code>Esperienza</code> dopo la conferma del
     * rimborso il destinatario è chi ha pagato
     *  * deve aggiornare lo stato di un pagamento
     *      * @param statoPagamento <code>{@link StatoPagamento}</code>
     * @param conto_destinatario altrimenti definito turista
     */
    void admin_turista(String conto_destinatario);


}
