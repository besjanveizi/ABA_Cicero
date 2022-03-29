package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;




import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;

import javax.naming.OperationNotSupportedException;

public abstract class AbstractGestore {

    /**
     * l' utente che attualmente sta utilizzando il sistema
     */
    protected Utente utente_corrente;

    /**
     * funzioni del database
     */
    protected DBManager dbManager;

    /**
     * le funzioni attualmente disponibili
     * @param dbManager <code>{@link DBManager}</code>
     */
    protected AbstractGestore(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     *
     * @param utente_corrente per operazione in cui il suo identificativo è necessario
     * @param dbManager operazioni disponibili
     */
    protected AbstractGestore(Utente utente_corrente,DBManager dbManager) {
        this.dbManager = dbManager;
        this.utente_corrente = utente_corrente;
    }

    /**
     * stampa in console, se implementato, la lista degli oggetti presenti
     */
    public abstract void show() throws OperationNotSupportedException;

}
