package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Amministratore;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Cicerone;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Turista;
import it.unicam.cs.ids2122.cicero.model.controllerRuoli.Ctrl_Utente;
import it.unicam.cs.ids2122.cicero.model.services.PersistenceErrorException;
import it.unicam.cs.ids2122.cicero.model.services.ServiceUtente;
import it.unicam.cs.ids2122.cicero.ruoli.*;


public class GestoreAutenticazione {

    private static GestoreAutenticazione instance = null;
    private final ServiceUtente serviceUtente;
    private final Piattaforma piattaforma;

    private GestoreAutenticazione(){
        piattaforma = Piattaforma.getInstance();
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
        try {
            IUtente u = serviceUtente.selectUtente(username, password);
            piattaforma.setCtrl_utente(getCtrl_utente(u.getUID(), username, u.getEmail(), password, u.getType()));
            result = true;
        } catch (PersistenceErrorException ignored) {}
        return result;
    }

    public void signUp(String username, String email, String password, UtenteType uType) {
        IUtente u = serviceUtente.upload(username, email, password, uType);
        piattaforma.setCtrl_utente(getCtrl_utente(u.getUID(), username, u.getEmail(), password, u.getType()));
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
}
