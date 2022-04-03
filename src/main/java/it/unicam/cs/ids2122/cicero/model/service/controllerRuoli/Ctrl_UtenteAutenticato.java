package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
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
    protected boolean switchMenu(int scelta) {
        boolean loop;
        switch (scelta) {
            case 1:
                logOut();
                loop = false;
                break;
            case 2:
                exit();
                loop = false;
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void logOut() {
        view.message("Arrivederci " + utente.getUsername() + "!!");
        Piattaforma.getInstance().resetCtrl_utente();
    }

    @Override
    protected void exit() {
        logOut();
        System.exit(0);
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
