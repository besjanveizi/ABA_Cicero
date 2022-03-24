package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.ruoli.UtenteAutenticato;
import it.unicam.cs.ids2122.cicero.view.IView;

/**
 * Rappresenta un gestore radice per un utente autenticato
 */
public class Ctrl_UtenteAutenticato extends Ctrl_UtenteGenerico implements Ctrl_Utente {

    protected UtenteAutenticato utente;

    public Ctrl_UtenteAutenticato(IView<String> view, UtenteAutenticato utente) {
        super(view);
        this.utente = utente;
        impostaMenu();
    }

    @Override
    public void mainMenu() {
        int scelta;
        do {
            view.message("Menu", menuItems);
            scelta = view.fetchChoice("Inserisci l'indice dell'operazione", menuItems.size());
        } while (switchMenu(scelta));
    }

    @Override
    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        if (scelta == 1) {
            logOut();
        } else {
            loop = super.switchMenu(scelta);
        }
        return loop;
    }

    protected void logOut() {
        // logout, delega gestoreAutenticazione
        System.out.println("hai scelto di fare logout");
    }

    private void impostaMenu() {
        // rimpiazza "1) Log in" con "1) Log out" nei menuItems
        menuItems.remove(0);
        menuItems.add(0, "1) Log out");
    }

    public UtenteAutenticato getLoggedUser() {
        return utente;
    }

}
