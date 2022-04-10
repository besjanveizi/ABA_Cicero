package it.unicam.cs.ids2122.cicero;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.services.ServiceSpostamento;

public class Demo {

    public static void main(String[] args) {
        Piattaforma p = Piattaforma.getInstance();
        //p.init();
        Percorso percorso = ServiceSpostamento.getInstance().downloadPercorso(1);
        System.out.println(percorso);
    }
}
