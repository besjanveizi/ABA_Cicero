package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta il gestore radice dell'utente non autenticato al sistema.
 */
public class Ctrl_UtenteGenerico implements Ctrl_Utente {

    protected IView<String> view;
    protected List<String> menuItems;

    public Ctrl_UtenteGenerico(IView<String> view) {
        this.view = view;
        menuItems = new ArrayList<>();
        impostaMenu();
        // gestoreRicerca(view) = new ..
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
