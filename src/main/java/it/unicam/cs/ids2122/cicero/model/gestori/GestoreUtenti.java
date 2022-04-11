package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.services.ServiceUtente;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * Classe che gestisce la collezione di <code>Utenti</code> presenti nella piattaforma cicero
 */
public class GestoreUtenti {
    private static GestoreUtenti instance = null;
    private Set<IUtente> allUtenti;

    private GestoreUtenti(){
        updateUtenti();
    }

    /**
     * @return l'istanza aggiornata del gestore degli <code>Utenti</code>.
     */
    public static GestoreUtenti getInstance(){
        if(instance==null)instance=new GestoreUtenti();
        return instance;
    }

    private void updateUtenti(){
        ServiceUtente service= ServiceUtente.getInstance();
        allUtenti=service.getUsers(UtenteType.CICERONE);
        allUtenti.addAll(service.getUsers(UtenteType.TURISTA));
        allUtenti.addAll(service.getUsers(UtenteType.ADMIN));
    }

    /**
     * ritorna tutti gli utenti che rispettano il predicato dato.
     * @param p predicato sugli utenti.
     * @return insieme degli Utenti che rispettano il predicato.
     */
    public Set<IUtente> getUtenti(Predicate<IUtente> p){
        return allUtenti.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * Elimina uno specifico utente registrato dalla piattaforma.
     * @param id identificativo dell'utente.
     */
    public void deleteUtente(int id){
        ServiceUtente service= ServiceUtente.getInstance();
        service.delete(id);
        updateUtenti();
    }

}