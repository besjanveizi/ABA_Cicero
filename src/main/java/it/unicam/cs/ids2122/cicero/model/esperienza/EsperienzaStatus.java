package it.unicam.cs.ids2122.cicero.model.esperienza;

/**
 * Rappresenta lo stato dell'<code>Esperienza</code>.
 */
public enum EsperienzaStatus {
    /**
     * L'<code>Esperienza</code> &egrave pubblica e si trova in attesa di convalida.
     */
    IDLE,

    /**
     * L'<code>Esperienza</code> &egrave valida, cio&egrave ha raggiunto il numero minimo di posti riservati per
     * essere eseguita.
     */
    VALIDA,

    /**
     * L'<code>Esperienza</code> &egrave cominciata. cio&egrave ha raggiunto il numero minimo di partecipanti
     * (quindi che hanno pagato) per essere eseguita.
     * Entrata in questo stato, non possono essere richiesti rimborsi da parte dei partecipanti.
     */
    IN_CORSO,

    /**
     * L'<code>Esperienza</code> &egrave stata cancellata. Il passaggio a questo stato può avvenire:<br>
     * <ul>
     *     <li> in automatico:</li>
     *     se non si raggiunge il numero di partecipanti (quindi che hanno pagato) entro la data
     *     d'inizio dell'<code>Esperienza</code>.
     *     <li> manualmente:</li>
     *     se il <code>Cicerone</code> o l'<code>Amministrazione</code> decidono di eliminare
     *     l'<code>Esperienza</code> dalla piattaforma.
     * </ul>
     */
    CANCELLATA,

    /**
     * L'<code>Esperienza</code> &egrave conclusa. Arrivata in questo stato, i partecipanti possono fare
     * richieste di rimborso e valutazioni all'<code>Esperienza</code>.
     */
    CONCLUSA,

    /**
     * L'<code>Esperienza</code> &egrave terminata. Arrivata in questo stato, i partecipanti non possono più fare
     * richieste di rimborso all'<code>Esperienza</code>.
     */
    TERMINATA
}
