package controller;

import model.esperienza.Tag;
import model.esperienza.Toponimo;
import model.ruoli.Cicerone;
import view.IConsoleView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Rappresenta un controller per un utente <code>Cicerone</code> che elabora le sue interazioni col sistema tramite la console.
 */
public class CiceroneCLIController implements CiceroneController {

    Cicerone cicerone;
    IConsoleView ciceroneView;

    /**
     * Crea un controller per l'utente <code>Cicerone</code>.
     * @param cicerone entit&agrave del modello rappresentante l'utente <code>Cicerone</code>.
     * @param ciceroneView interfaccia a linea di comando dell'utente <code>Cicerone</code>.
     */
    public CiceroneCLIController(Cicerone cicerone, IConsoleView ciceroneView) {
        this.cicerone = cicerone;
        this.ciceroneView = ciceroneView;
    }

    @Override
    public void creaNuovaEsperienza() {
        String nomeE = ciceroneView.ask("Imposta in nome dell'esperienza");

        // TODO: check exceptions for dates
        LocalDateTime dI = LocalDateTime.parse(ciceroneView.ask(("Imposta data inizio")));
        LocalDateTime dF = LocalDateTime.parse(ciceroneView.ask(("Imposta data fine")));

        // TODO: check exceptions for ints
        int minP, maxP;
        do {
            minP = Integer.parseInt(ciceroneView.ask("Imposta numero min partecipanti"));
        } while(minP < 0);

        do {
            maxP = Integer.parseInt(ciceroneView.ask("Imposta numero max partecipanti"));
        } while (maxP < minP);

        int maxRiserva = Integer.parseInt(ciceroneView.ask("Imposta numero max di giorni riserva (minimo 2)"));
        if (maxRiserva < 2) {
            ciceroneView.message("Hai inserito un valore sotto alla soglia minima per la riserva.\n" +
                    "Il numero max di riserva è stato impostato al valore minimo di default: 2");
            maxRiserva = 2;
        }

        List<Toponimo> toponimi = null;
        List<Tag> tags = null;
        // TODO: get list toponimi & tags form user

        boolean accetta = parseToBool(ciceroneView.ask("Vuoi confermare la creazione dell'esperienza [y/n]:"));

        if (accetta) {
            InfoEsperienza cliE = new InfoEsperienza(nomeE, dI, dF, minP, maxP, maxRiserva, toponimi, tags);
            cicerone.creaEsperienza(cliE);
            ciceroneView.message("La creazione dell'esperienza è avvenuta con successo");
        }
        else {
            ciceroneView.message("La creazione dell'esperienza è stata cancellata");
        }
    }

    // TODO: da spostare in package .util
    private boolean parseToBool(String risposta) {
        return Arrays.asList("y","Y","Yes","YES","yes","true","True","TRUE").contains(risposta);
    }
}