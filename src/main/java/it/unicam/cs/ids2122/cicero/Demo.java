package it.unicam.cs.ids2122.cicero;

import it.unicam.cs.ids2122.cicero.model.prenotazione.sistema.ControllerSistema;

import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) throws SQLException {
        ControllerSistema controllerSistema = new ControllerSistema();
        controllerSistema.menu();
    }
}
