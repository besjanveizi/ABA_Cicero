package it.unicam.cs.ids2122.cicero.model.entities.segnalazione;

public interface Segnalazione {
    /**
     * Recupera l'id della segnalazione.
     * @return intero che identifica la segnalazione.
     */
    int getId();

    /**
     * Recupera l'id dell'utente che ha effettuato la segnalazione.
     * @return intero che identifica l'utente creatore.
     */
    int getUId();

    /**
     * Recupera l'id dell'esperienza a cui si riferisce la segnalazione.
     * @return intero che identifica l'esperienza.
     */
    int getEsperienzaId();

    /**
     * Recupera la descrizione della segnalazione.
     * @return Stringa di caratteri contenente la descrizione.
     */
    String getDescrizione();

    /**
     * Recupera lo stato della segnalazione.
     * @return stato della segnalazione.
     */
    SegnalazioneStatus getState();
}
