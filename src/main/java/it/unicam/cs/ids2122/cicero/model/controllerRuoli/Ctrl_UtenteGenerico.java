package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.gestori.GestoreAutenticazione;
import it.unicam.cs.ids2122.cicero.model.gestori.GestoreRicerca;
import it.unicam.cs.ids2122.cicero.model.gestori.GestoreTag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.model.gestori.GestoreAree;
import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Rappresenta il gestore radice dell'utente non autenticato al sistema.
 */
public class Ctrl_UtenteGenerico implements Ctrl_Utente {

    protected final IView<String> view;
    protected List<String> menuItems;
    private final GestoreRicerca gestoreRicerca;
    private Set<Esperienza> lastRicerca;
    private final Logger logger = Logger.getLogger(Piattaforma.class.getName());
    private final GestoreAutenticazione gestoreAutenticazione;

    public Ctrl_UtenteGenerico() {
        this.view = Piattaforma.getInstance().getView();
        menuItems = new ArrayList<>();
        impostaMenu();
        gestoreAutenticazione = GestoreAutenticazione.getInstance();
        gestoreRicerca = new GestoreRicerca();
        lastRicerca = new HashSet<>();
    }

    @Override
    public void mainMenu() {
        int scelta;
        do {
            view.message("\n--MENU PRINCIPALE--", menuItems);
            scelta = view.fetchChoice("Inserisci l'indice dell'operazione", menuItems.size());
        } while (switchMenu(scelta));
    }

    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        switch (scelta) {
            case 1:
                authenticate();
                loop = false;
                break;
            case 2:
                exit();
                loop = false;
                break;
            case 3:
                cercaEsperienze();
                break;
            default:
                logger.info("Input non valido");
        }
        return loop;
    }

    private void authenticate() {
        do {
            view.message("\n--AUTENTICAZIONE--\nScegli una delle due operazioni:");
            int choice = view.fetchChoice("1) LogIn\n2) SignIn\n3) Torna al menù principale", 3);
            if (choice == 2) signUp();
            else if (choice == 3) return;
            else {
                view.message("\n--LOGIN--");
                String username = view.ask("Inserisci lo username:");
                String password = view.ask("Inserisci la password:");
                if (gestoreAutenticazione.login(username, password)) break;
            }
        } while (true);
    }

    private void signUp() {
        String email;
        boolean backtrack = false;
        view.message("\n--REGISTRAZIONE--");
        do {
            email = view.ask("Inserisci l'email: ");
            if (!gestoreAutenticazione.isAlreadySignedIn(email))
                break;
            else {
                logger.info("L'email è già registrata ad un profilo nel sistema.");
                view.message("Vuoi tornare all'autenticazione? [Y,n]");
                backtrack = view.fetchBool();
            }
        } while (!backtrack);
        if (backtrack) return;
        String username;
        do {
            username = view.ask("Scegli lo username: ");
            if (!gestoreAutenticazione.isAlreadyTaken(username))
                break;
            else {
                logger.info("Lo username scelto è già registrato ad un profilo nel sistema con un'altra email.");
                view.message("Vuoi tornare all'autenticazione? [Y,n]");
                backtrack = view.fetchBool();
            }
        } while (!backtrack);
        if (backtrack) return;
        String password;
        while (true) {
            password = view.ask("Inserisci la password: ");
            if (view.ask("Reinserisci la password: ").equals(password)) break;
            else logger.info("Le due password inserite non coincidono: bisogna reinserirle");
        }
        UtenteType utype = UtenteType.fetchUtype(
                view.fetchChoice("Scegli il tipo di utente:\n1) Cicerone;\n2) Turista;", 2));

        gestoreAutenticazione.signUp(username, email, password, utype);
    }

    protected void exit() {
        view.message("Arrivederci!");
        System.exit(0);
    }

    protected void cercaEsperienze() {
        String filtroNome= view.ask("Inserire una stringa per filtrare il nome delle esperienze: ");
        Set<Area> filtroAree = impostaFiltroAree();
        Set<Tag> filtroTags = impostaFiltroTag();
        lastRicerca = gestoreRicerca.ricerca(filtroNome,filtroTags,filtroAree);
        showEsperienze(lastRicerca);
        if (view.fetchChoice("\n\n1) Selezionare un'esperienza per vedere maggiori dettagli" +
                "\n2) Torna al menu principale", 2) == 1)
            view.message(selezionaEsperienza(lastRicerca).toString());
    }

    private Set<Area> impostaFiltroAree(){
        Set<Area> allAree = GestoreAree.getInstance().getAree();
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

    private Set<Tag> impostaFiltroTag(){
        Set<Tag> tagsApprovati = GestoreTag.getInstance().getTags(e -> e.getState().equals(TagStatus.APPROVATO));
        Set<String> viewSet = tagsApprovati.stream().map(Tag::getName).collect(Collectors.toSet());
        view.message("Scegli i tag con cui filtrare la ricerca", viewSet);
        Set<String> viewSubSet = view.fetchSubSet(viewSet);
        Set<Tag> chosenTags = new HashSet<>();
        if (!viewSubSet.isEmpty())
            for (String tagName : viewSubSet)
                tagsApprovati.stream().filter(t -> t.getName().equals(tagName)).findFirst().ifPresent(chosenTags::add);
        return chosenTags;
    }

    private void showEsperienze(Set<Esperienza> esperienze){
        if(esperienze.isEmpty()){
            view.message("Non sono state trovate esperienze che rientrano nei filtri imposti per la ricerca.");
        } else {
            view.message("Risultati della ricerca:", esperienze.stream()
                                                                        .map(Esperienza::shortToString)
                                                                        .collect(Collectors.toSet()));
        }
    }

    /**
     * Permette la selezione di un'{@code Esperienza} da un insieme.
     * @param esperienze {@code Set} di esperienze su cui effettuare la selezione.
     * @return l'{@code Esperienza} selezionata.
     */
    protected Esperienza selezionaEsperienza(Set<Esperienza> esperienze) {
        List<String> viewList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        int i = 1;
        for (Esperienza e :esperienze) {
            viewList.add(i++ + ") " + e.getName());
            idList.add(e.getId());
        }
        view.message("Esperienze:", viewList);
        int indice = view.fetchChoice("Scegli l'indice dell'esperienza", viewList.size());
        int idEsperienza = idList.get(indice-1);
        return esperienze.stream().filter(e -> e.getId() == idEsperienza).findFirst().get();
    }

    private void impostaMenu() {
        menuItems.add("1) Effettua l'autenticazione");
        menuItems.add("2) Termina");
        menuItems.add("3) Cerca Esperienze");
    }

}
