package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.service.ricerca.GestoreRicerca;
import it.unicam.cs.ids2122.cicero.model.tag.GestoreTag;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.model.territorio.GestoreAree;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rappresenta il gestore radice dell'utente non autenticato al sistema.
 */
public class Ctrl_UtenteGenerico implements Ctrl_Utente {

    protected IView<String> view;
    protected List<String> menuItems;
    private GestoreAree gestoreAree;
    private GestoreTag gestoreTag;
    private GestoreRicerca gestoreRicerca;
    private Set<Esperienza> lastRicerca;

    public Ctrl_UtenteGenerico(IView<String> view) {
        this.view = view;
        menuItems = new ArrayList<>();
        impostaMenu();
        gestoreAree = new GestoreAree();
        gestoreTag= new GestoreTag();
        gestoreRicerca=new GestoreRicerca();
        // gestoreAutenticazione(view) = new ..
    }

    @Override
    public void mainMenu() {
        int scelta;
        do {
            view.message("Menu", menuItems);
            scelta = view.fetchChoice("Inserisci l'indice dell'operazione", menuItems.size());
        } while (switchMenu(scelta));
    }

    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        switch (scelta) {
            case 1:
                logIn();
                break;
            case 2:
                exit();
                loop = false;
                break;
            case 3:
                cercaEsperienze();
                break;
        }
        return loop;
    }

    protected void exit() {
        //termina l'applicazione
        System.out.println("hai scelto di terminare l'app");
    }

    protected void cercaEsperienze() {
        // cerca esperienze, delega a gestoreRicerca
        System.out.println("hai scelto di cercare esperienza");
        String filtroNome= view.ask("Inserire una stringa per filtrare il nome delle esperienze: ");
        Set<Area> filtroAree = impostaAree();
        Set<Tag> filtroTags = impostaTag();
        lastRicerca =gestoreRicerca.ricerca(filtroNome,filtroTags,filtroAree);
        showEsperienzeTrovate(lastRicerca);
    }

    private Set<Area> impostaAree(){
        Set<Area> allAree = gestoreAree.getAree();
        Set<String> viewSet = allAree.stream().map(Area::getToponimo).collect(Collectors.toSet());
        view.message("Seleziona i toponimi con cui filtrare la ricerca",viewSet);
        Set<String> viewSubSet=view.fetchSubSet(viewSet);
        Set<Area> chosenAree = new HashSet<>();
        if(!viewSubSet.isEmpty()){
            for(String toponimo : viewSubSet){
                allAree.stream().filter(a -> a.getToponimo().equals(toponimo)).findFirst().ifPresent(chosenAree::add);
            }
        }
        return chosenAree;
    }

    private Set<Tag> impostaTag(){
        Set<Tag> tagsApprovati = gestoreTag.getTags(e -> e.getState().equals(TagStatus.APPROVATO));
        Set<String> viewSet = tagsApprovati.stream().map(Tag::getName).collect(Collectors.toSet());
        view.message("Scegli i tag con cui filtrare la ricerca", viewSet);
        Set<String> viewSubSet = view.fetchSubSet(viewSet);
        Set<Tag> chosenTags = new HashSet<>();
        if (!viewSubSet.isEmpty())
            for (String tagName : viewSubSet)
                tagsApprovati.stream().filter(t -> t.getName().equals(tagName)).findFirst().ifPresent(chosenTags::add);
        return chosenTags;
    }

    private void showEsperienzeTrovate(Set<Esperienza> esperienze){
        Set<String> esperienzeShort = esperienze.stream().map(Esperienza::shortToString).collect(Collectors.toSet());
        view.message("Esperienze trovate:",esperienzeShort);
        /*
        view.message("Vuoi selezionare una specifica esperienza?");
        if(view.fetchBool()){
            while(true){
                String nome=view.ask("Inserire il nome dell'esperienza di cui si vogliono visualizzare i dettagli: ");
                //...
            }
        }
         */
    }

    protected void logIn() {
        // login, delega gestoreAutenticazione
        System.out.println("hai scelto di fare login");
    }

    private void impostaMenu() {
        menuItems.add("1) Log In");
        menuItems.add("2) Termina");
        menuItems.add("3) Cerca Esperienze");
    }

}
