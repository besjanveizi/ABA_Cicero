package it.unicam.cs.ids2122.cicero.ruoli;

import it.unicam.cs.ids2122.cicero.model.service.ricerca.BoundaryRicerca;
import it.unicam.cs.ids2122.cicero.model.esperienza.SimpleEsperienza;

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
