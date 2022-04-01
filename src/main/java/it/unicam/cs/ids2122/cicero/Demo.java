package it.unicam.cs.ids2122.cicero;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;

import java.sql.SQLException;

public class Demo {

    public static void main(String[] args) throws SQLException {
        Piattaforma p = Piattaforma.getInstance();
        p.init();
    }
}
