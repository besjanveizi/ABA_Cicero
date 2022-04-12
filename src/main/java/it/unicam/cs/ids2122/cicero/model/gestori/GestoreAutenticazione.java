package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.services.PersistenceErrorException;
import it.unicam.cs.ids2122.cicero.model.services.ServiceUtente;
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


    public boolean login(String username, String password) {
        boolean result = false;
        UtenteAutenticato utenteAutenticato;
        try {
            utenteAutenticato = serviceUtente.download(username, password);
            Piattaforma.getInstance().setCtrl_utente(utenteAutenticato);
            result = true;
        } catch (PersistenceErrorException ignored) {}
        return result;
    }

    public void signUp(String username, String email, String password, UtenteType uType) {
        UtenteAutenticato utenteAutenticato = serviceUtente.upload(username, email, password, uType);;
        Piattaforma.getInstance().setCtrl_utente(utenteAutenticato);
    }


    /**
     * Controlla se l'email data &egrave gi&agrave stata registrata ad un profilo.
     * @param email email ricercata.
     * @return {@code true} se &egrave gi&agrave stata registrata, altrimenti false.
     */
    public boolean isAlreadySignedIn(String email) {
        return serviceUtente.fetchThroughEmail(email).isPresent();
    }

    /**
     * COntrolla se l'username dato è gia stato registrato ad un profilo.
     * @param username username ricercato.
     * @return {@code true} se è gia stato registrato, altrimenti false.
     */
    public boolean isAlreadyTaken(String username) {
        return serviceUtente.fetchThroughUsername(username).isPresent();
    }
}
