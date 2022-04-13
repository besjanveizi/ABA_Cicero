package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.controllerRuoli.*;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.view.CLIView;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.logging.*;

public class Piattaforma {

    private static Piattaforma instance;
    private static Ctrl_Utente ctrl_utente;

    private IView<String> cli_view;

    protected static final Logger logger = Logger.getLogger(Piattaforma.class.getName());
    static {
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

    private Piattaforma()  {
        DBManager.getInstance().testConnection();
        Bacheca.getInstance();
    }

    public static Piattaforma getInstance() {
        if (instance == null)
            instance = new Piattaforma();
        return instance;
    }

    public void init() {
        cli_view = new CLIView();
        ctrl_utente = new Ctrl_UtenteGenerico();
        while (true) {
            ctrl_utente.mainMenu();
        }
    }

    public void setCtrl_utente(Ctrl_Utente new_ctrl) {
        ctrl_utente = new_ctrl;
    }

    public void resetCtrl_utente() {
        ctrl_utente = new Ctrl_UtenteGenerico();
    }

    public IView<String> getView() {
        return cli_view;
    }
}
