package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.controllerRuoli.*;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.ruoli.*;
import it.unicam.cs.ids2122.cicero.view.CLIView;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.logging.*;

public class Piattaforma {

    private static Piattaforma instance;
    private static Ctrl_Utente ctrl_utente;
    private IView<String> cli_view;
    private Logger logger;

    private Piattaforma()  {
        setupLogger();
        DBManager.getInstance().testConnection();
    }

    public static Piattaforma getInstance() {
        if (instance == null)
            instance = new Piattaforma();
        return instance;
    }

    public void setCtrl_utente(IUtente utente) {
        int uid = utente.getUID();
        String username = utente.getUsername();
        String email = utente.getEmail();
        String password = utente.getPassword();
        switch (utente.getType()) {
            case ADMIN:
                ctrl_utente = new Ctrl_Amministratore(cli_view, new Amministratore(uid, username, email, password));
                break;
            case CICERONE:
                ctrl_utente = new Ctrl_Cicerone(cli_view, new Cicerone(uid, username, email, password));
                break;
            case TURISTA:
                ctrl_utente = new Ctrl_Turista(cli_view, new Turista(uid, username, email, password));
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

    private void setupLogger() {
        logger = Logger.getLogger(Piattaforma.class.getName());
        logger.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        ch.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                return record.getMessage() + "\n";
            }
        });
        logger.addHandler(ch);
    }
}
