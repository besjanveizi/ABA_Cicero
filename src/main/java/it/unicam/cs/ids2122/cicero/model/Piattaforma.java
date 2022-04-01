package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.service.controllerRuoli.*;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.ruoli.Amministratore;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteAutenticato;
import it.unicam.cs.ids2122.cicero.view.CLIView;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;

public class Piattaforma {

    private static Piattaforma instance;
    private static Ctrl_Utente ctrl_utente;
    private IView<String> cli_view;

    private final DBManager dbManager;

    private Piattaforma() throws SQLException {
        dbManager = DBManager.getInstance();
    }

    public static Piattaforma getInstance() throws SQLException {
        if (instance == null)
            instance = new Piattaforma();
        return instance;
    }

    public void setCtrl_utente(UtenteAutenticato utente) {
        String username = utente.getUsername();
        String email = utente.getEmail();
        switch (utente.getType()) {
            case ADMIN:
                ctrl_utente = new Ctrl_Amministratore(cli_view, new Amministratore(username, email));
                break;
            case CICERONE:
                ctrl_utente = new Ctrl_Cicerone(cli_view, new Cicerone(username, email));
                break;
            case TURISTA:
                ctrl_utente = new Ctrl_Turista(cli_view, new Turista(username, email));
                break;
        }
    }

    public void init() {
        cli_view = new CLIView();
        ctrl_utente = new Ctrl_UtenteGenerico(cli_view);
        while (true) {
            ctrl_utente.mainMenu();
        }
    }

    public void resetCtrl_utente() {
        ctrl_utente = new Ctrl_UtenteGenerico(cli_view);
    }
}
