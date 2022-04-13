package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Amministratore;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Cicerone;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Turista;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Utente;
import it.unicam.cs.ids2122.cicero.model.services.PersistenceErrorException;
import it.unicam.cs.ids2122.cicero.model.services.ServiceUtente;
import it.unicam.cs.ids2122.cicero.ruoli.*;
import it.unicam.cs.ids2122.cicero.view.IView;


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
            Ctrl_Utente ctrl_utente = getCtrl_utente(utenteAutenticato.getUID(), username, utenteAutenticato.getEmail(),
                    password, utenteAutenticato.getType());
            Piattaforma.getInstance().setCtrl_utente(ctrl_utente);
            result = true;
        } catch (PersistenceErrorException ignored) {}
        return result;
    }

    public void signUp(String username, String email, String password, UtenteType uType) {
        UtenteAutenticato utente = serviceUtente.upload(username, email, password, uType);
        int uid = utente.getUID();
        Ctrl_Utente ctrl_utente = getCtrl_utente(uid, username, email, password, uType);
        Piattaforma.getInstance().setCtrl_utente(ctrl_utente);
    }

    private Ctrl_Utente getCtrl_utente(int uid, String username, String email, String password, UtenteType uType) {
        Ctrl_Utente ctrl_utente = null;
        switch (uType) {
            case ADMIN:
                ctrl_utente = new Ctrl_Amministratore(new Amministratore(uid, username, email, password));
                break;
            case CICERONE:
                ctrl_utente = new Ctrl_Cicerone(new Cicerone(uid, username, email, password));
                break;
            case TURISTA:
                ctrl_utente = new Ctrl_Turista(new Turista(uid, username, email, password));
                break;
        }
        return ctrl_utente;
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
