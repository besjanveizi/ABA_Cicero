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

    protected IView<String> view;
    protected List<String> menuItems;
    private GestoreRicerca gestoreRicerca;
    private Set<Esperienza> lastRicerca;
    private Logger logger = Logger.getLogger(Piattaforma.class.getName());
    private GestoreAutenticazione gestoreAutenticazione;

    public Ctrl_UtenteGenerico(IView<String> view) {
        this.view = view;
        menuItems = new ArrayList<>();
        impostaMenu();
        gestoreAutenticazione = GestoreAutenticazione.getInstance();
        gestoreRicerca=new GestoreRicerca();
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
            if (choice == 2) signIn();
            else if (choice == 3) return;
            else {
                view.message("\n--LOGIN--");
                String username = view.ask("Inserisci lo username:");
                String password = view.ask("Inserisci la password:");
                if (gestoreAutenticazione.login(username, password)) break;
            }
        } while (true);
    }

    private void signIn() {
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
        Set<Area> filtroAree = impostaAree();
        Set<Tag> filtroTags = impostaTag();
        lastRicerca = gestoreRicerca.ricerca(filtroNome,filtroTags,filtroAree);
        showEsperienzeTrovate(lastRicerca);
    }

    private Set<Area> impostaAree(){
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

    private Set<Tag> impostaTag(){
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

    private void showEsperienzeTrovate(Set<Esperienza> esperienze){
        Set<String> esperienzeShort = esperienze.stream().map(Esperienza::toString).collect(Collectors.toSet());
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

    private void impostaMenu() {
        menuItems.add("1) Effettua l'autenticazione");
        menuItems.add("2) Termina");
        menuItems.add("3) Cerca Esperienze");
    }

}
