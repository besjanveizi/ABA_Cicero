package it.unicam.cs.ids2122.cicero.model.prenotazione.invito;

import java.time.LocalDateTime;

/**
 * Restituisce le caratteristiche essenziali per un <code>{@link Invito}</code>
 */
public interface PropInvito extends Invito {

    int getId_invito();

    int getId_mittente() ;

    int getId_esperienza();

    String getEmail_destinatario();

    LocalDateTime getData_creazione();

    LocalDateTime getData_scadenza_riserva();

    int getPosti_riservati();

}
