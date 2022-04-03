package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.invito;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;

import java.time.LocalDateTime;

/**
 *  Facade per gestire l' invito
 */
public interface Invito {

    /**
     * recupero id invito, generato dal DB
     * @return id
     */
    int getId_invito();

    /**
     * recupero invito
     * @return id dell' utente che ha creato l' invito
     */
    int getId_mittente() ;

    /**
     * recupero id dell' {@code Esperienza}. a cui riferisce l' invito
     * @return id dell'  {@link Esperienza}
     */
    int getId_esperienza();

    /**
     * L' indirizzo a cui verrà spedita
     * @return la mail del destinatario
     */
    String getEmail_destinatario();

    /**
     * La data di creazione dell' invito
     * @return data di creazione
      */
    LocalDateTime getData_creazione();

    /**
     * La data di scadenza è la somma tra la data di creazione
     * e la variabile maxGiorniRiserva definita in {@code Esperienza}.
     * @return data di validità
     */
    LocalDateTime getData_scadenza_riserva();

    /**
     * Rappresenta i posti riservati dell {@code Esperienza}.
     * @return i posti riservati
     */
    int getPosti_riservati();

}
