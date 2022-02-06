package model.ruoli;

import controller.BoundaryRicerca;
import model.esperienza.SimpleEsperienza;

public class DefaultUtente implements Utente, UtenteNonRegistrato {
    private BoundaryRicerca InterfacciaRicerca;
    public DefaultUtente(){
        InterfacciaRicerca=new BoundaryRicerca();
    }

    public void cercaEsperienze() {
        InterfacciaRicerca.ricerca();
    }

    @Override
    public void showAllEsperienze() {
        InterfacciaRicerca.showAllEsperienze();
    }

    @Override
    public void showEsperienza(SimpleEsperienza esperienza) {
        InterfacciaRicerca.showEsperienza(esperienza);
    }
    @Override
    public void effettuaAutenticazione() { }

    @Override
    public void effettuaRegistrazione() { }
}
