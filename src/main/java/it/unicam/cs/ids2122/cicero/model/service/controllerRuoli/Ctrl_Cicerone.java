package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.view.IView;

/**
 * Rappresenta un gestore radice un utente <code>Cicerone</code> che elabora le sue interazioni con il sistema.
 */
public class Ctrl_Cicerone extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    public Ctrl_Cicerone(IView<String> view, Cicerone cicerone) {
        super(view, cicerone);
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
        if (scelta == 4) {
            creaEsperienza();
        } else {
            loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void impostaMenu() {
        menuItems.add("4) Crea Esperienza");
    }

    /**
     * Permette di elaborare la creazione di una nuova <code>Esperienza</code>.
     */
    void creaEsperienza() {
        System.out.println("Hai scelto crea esperienza");
    }
}
