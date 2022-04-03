package it.unicam.cs.ids2122.cicero.model.esperienza;

/**
 * Rappresenta lo stato dell'{@code Esperienza}.
 */
public enum EsperienzaStatus {
    /**
     * L'{@code Esperienza} &egrave pubblica e si trova in attesa di convalida.
     */
    IDLE(0),

    /**
     * L'{@code Esperienza} &egrave valida, cio&egrave ha raggiunto il numero minimo di posti riservati per
     * essere eseguita.
     */
    VALIDA(1),

    /**
     * L'{@code Esperienza} &egrave cominciata. cio&egrave ha raggiunto il numero minimo di partecipanti
     * (quindi che hanno pagato) per essere eseguita.
     * Entrata in questo stato, non possono essere richiesti rimborsi da parte dei partecipanti.
     */
    IN_CORSO(2),

    /**
     * L'{@code Esperienza} &egrave stata cancellata. Il passaggio a questo stato può avvenire:<br>
     * <ul>
     *     <li> in automatico:</li>
     *     se non si raggiunge il numero di partecipanti (quindi che hanno pagato) entro la data
     *     d'inizio dell'{@code Esperienza}.
     *     <li> manualmente:</li>
     *     se il {@code Cicerone} o l'{@code Amministratore} decidono di eliminare
     *     l'{@code Esperienza} dalla piattaforma.
     * </ul>
     */
    CANCELLATA(3),

    /**
     * L'{@code Esperienza} &egrave conclusa. Arrivata in questo stato, i partecipanti possono fare
     * richieste di rimborso e valutazioni all'{@code Esperienza}.
     */
    CONCLUSA(4),

    /**
     * L'{@code Esperienza} &egrave terminata. Arrivata in questo stato, i partecipanti non possono più fare
     * richieste di rimborso all'{@code Esperienza}.
     */
    TERMINATA(5);

    private final int code;

    EsperienzaStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EsperienzaStatus fetchStatus(int eCode) {
        switch (eCode) {
            case 0: return EsperienzaStatus.IDLE;
            case 1: return EsperienzaStatus.VALIDA;
            case 2: return EsperienzaStatus.IN_CORSO;
            case 3: return EsperienzaStatus.CANCELLATA;
            case 4: return EsperienzaStatus.CONCLUSA;
            case 5: return EsperienzaStatus.TERMINATA;
            default: return null;
        }
    }

}
