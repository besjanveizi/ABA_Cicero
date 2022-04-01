package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.view.IView;

public class Ctrl_Turista extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    public Ctrl_Turista(IView<String> view, Turista turista) {
        super(view, turista);
        impostaMenu();
    }

    @Override
    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        switch (scelta) {
            case 4:
                prenotaEsperienza();
                break;
            case 5:
                pagaPrenotazione();
                break;
            case 6:
                invitaEsperienza();
                break;
            case 7:
                gestisciInvitiRicevuti();
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void gestisciInvitiRicevuti() {
        // delega al gestore degli inviti di mostrare gli inviti
        // permetti la scelta dell'invito e chiedi cosa vuole farci (accetta/rifiuta)
        // delega al gestore di inviti di gestire l'operazione scelta
    }

    private void invitaEsperienza() {
        // scegli l'esperienza tra le esperienze della bacheca
        // se possibile fare un invito, gestisci l'inserimento dei parametri per costruire l'invito
        // delega al gestore degli inviti di costruirlo e aggiungerlo al set degli inviti
    }

    private void prenotaEsperienza() {
        // segui diagramma di sequenza
    }

    private void pagaPrenotazione() {
    }

    private void impostaMenu() {
        menuItems.add("4) Prenota Esperienza");
        menuItems.add("5) Paga Prenotazione");
        menuItems.add("6) Invita ad una Esperienza");
        menuItems.add("7) Gestisci Inviti");
    }
}
