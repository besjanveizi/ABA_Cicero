package it.unicam.cs.ids2122.cicero;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;

public class Demo {

    public static void main(String[] args) {
        Piattaforma p = Piattaforma.getInstance();
        p.init();
    }
}
