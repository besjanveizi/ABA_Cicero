package it.unicam.cs.ids2122.cicero.persistence.services;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.persistence.PGManager;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteAutenticato;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;

import java.util.*;
import java.util.logging.Logger;

/**
 * Singleton Service class per operazioni di persistenza riguardanti gli utenti.
 */
public class ServiceUtenti extends AbstractService<IUtente> {

    private static ServiceUtenti instance = null;

    private ServiceUtenti(){};

    public static ServiceUtenti getInstance(){
        if (instance == null)
            instance = new ServiceUtenti();
        return instance;
    }

    private final String base_query = "SELECT uid, username, email, password, user_type FROM utenti_registrati";

    /**
     * Recupera tutti gli utenti registrati dal database del tipo dato.
     * @param utenteType tipo dell'{@code IUtente}.
     * @return {@code Set} di {@link IUtente} che sono di tipo {@code utenteType}.
     */
    public Set<IUtente> getUsers(UtenteType utenteType) {
        return execute(base_query + " WHERE user_type = " + utenteType.getCode() + ";");
    }

    /**
     * Recupera, se disponibile nel database, l'utente il cui {@code username} &egrave quello dato.
     * @param username username dell'utente che si sta cercando.
     * @return un'{@code Optional} che descrive l'{@link IUtente} cercato,
     * altrimenti un {@code Optional} vuoto se la ricerca nel database non ha avuto successo.
     */
    public Optional<IUtente> getUser(String username) {
        return execute(base_query + " WHERE username = '" + username + "';").stream().findFirst();
    }

    /**
     * Controlla se lo username dato &egrave gi&agrave stato registrato ad un profilo.
     * @param username username ricercato.
     * @return {@code true} se &egrave gi&agrave stato registrato, altrimenti false.
     */
    public boolean isAlreadySignedIn(String username) {
        return getUser(username).isPresent();
    }

    /**
     * Autentica un {@code IUtente} con le credenziali inserite.
     * @param username username dell'{@code IUtente}.
     * @param password password dell'{@code IUtente}.
     */
    public void login(String username, String password) {
        Set<IUtente> resultSet = execute(base_query + " WHERE username = '" + username + "' AND password = '" + password + "'");
        if (!resultSet.isEmpty()) {
            IUtente utente = resultSet.stream().findFirst().get();
            UtenteAutenticato utenteAutenticato = new UtenteAutenticato(utente.getUsername(), utente.getEmail(), utente.getType());
            Piattaforma.getInstance().setCtrl_utente(utenteAutenticato);
        }
        else
            Logger.getLogger(PGManager.class.getName()).warning("Authentication error: couldn't log in.\n");
    }

    @Override
    public Set<IUtente> parseDataResult(TreeMap<String, HashMap<String, String>> users) {
        Set<IUtente> resultSet = new HashSet<>();
        int id, user_type = 2;
        String username = "", email = "", password;

        for (Map.Entry<String, HashMap<String, String>> firstEntry : users.entrySet()) {
            id = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "username": username = val; break;
                    case "email": email = val; break;
                    case "password": password = val; break;
                    case "user_type": user_type = Integer.parseInt(val); break;
                    default: break;
                }
            }
            resultSet.add(new UtenteAutenticato(username, email, UtenteType.tipoUtente(user_type)));
        }
        return resultSet;
    }

}