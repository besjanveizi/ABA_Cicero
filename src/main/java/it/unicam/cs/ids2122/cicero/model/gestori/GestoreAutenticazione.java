package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.services.ServiceUtente;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteAutenticato;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;


public class GestoreAutenticazione {

    private static GestoreAutenticazione instance = null;
    private final ServiceUtente serviceUtente;

    private GestoreAutenticazione(){
        serviceUtente = ServiceUtente.getInstance();
    }

    /**
     * @return l'istanza aggiornata del gestore degli <code>Utenti</code>.
     */
    public static GestoreAutenticazione getInstance(){
        if(instance==null)instance=new GestoreAutenticazione();
        return instance;
    }


    public void logIn(String username, String password) {

    }

    public void signIn(String username, String email, String password, UtenteType uType) {
        UtenteAutenticato utenteAutenticato = serviceUtente.signIn(username, email, password, uType);;
        Piattaforma.getInstance().setCtrl_utente(utenteAutenticato);
    }
}
