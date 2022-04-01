package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.esperienza.GestoreEsperienze;
import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.GestorePercorso;
import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.tag.GestoreTag;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.model.territorio.GestoreAree;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rappresenta un gestore radice un utente <code>Cicerone</code> che elabora le sue interazioni con il sistema.
 */
public class Ctrl_Cicerone extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    private GestoreAree gestoreAree;
    private GestorePercorso gestorePercorso;
    private GestoreTag gestoreTag;
    private GestoreEsperienze gestoreEsperienze;

    public Ctrl_Cicerone(IView<String> view, Cicerone cicerone) {
        super(view, cicerone);
        impostaMenu();
        gestoreAree = GestoreAree.getInstance();
        gestorePercorso = new GestorePercorso(view, gestoreAree);
        gestoreTag = GestoreTag.getInstance();
        gestoreEsperienze = GestoreEsperienze.getInstance(cicerone);
    }

    @Override
    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        if (scelta == 4) {
            creaEsperienza();
        } else {
            loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void impostaMenu() {
        menuItems.add("4) Crea Esperienza");
    }

    /**
     * Permette di elaborare la creazione di una nuova <code>Esperienza</code>.
     */
    private void creaEsperienza() {
        LocalDateTime now = LocalDateTime.now();
        String nomeE = view.ask("Inserisci il nome dell'esperienza:");
        String descrizioneE = view.ask("Inserisci una descrizione per l'esperienza:");

        LocalDateTime dI, dF;
        while (true) {
            view.message("Inserisci la data d'inizio dell'esperienza");
            dI = view.fetchDate();
            if (dI.isAfter(now.plusHours(2L))) break;
            else view.message("ERRORE: La data d'inizio deve essere successiva di almeno due ore da adesso");
        }
        while (true) {
            view.message("Inserisci la data di termine dell'esperienza");
            dF = view.fetchDate();
            if (dF.isAfter(dI)) break;
            else view.message("ERRORE: La data di fine deve essere successiva alla data d'inizio");
        }

        int minP, maxP;
        while (true) {
            view.message("Imposta numero min partecipanti");
            minP = view.fetchInt();
            if (minP > 0) break;
            else view.message("ERRORE: Il numero minimo di partecipanti deve essere maggiore di 0");
        }

        while (true) {
            view.message("Imposta numero max partecipanti");
            maxP = view.fetchInt();
            if (maxP > minP) break;
            else view.message("ERRORE: Il numero massimo di partecipanti deve essere maggiore del minimo inserito");
        }

        Percorso percorso = gestorePercorso.creaPercorso();

        view.message("Inserisci il costo individuale dell'esperienza");
        Money costoIndividuale = view.fetchMoney();

        int maxRiserva = 2;
        view.message("Imposta numero max di giorni riserva (minimo 2)");
        int tempVal = view.fetchInt();
        if (tempVal < 2)
            view.message("Hai inserito un valore sotto alla soglia minima per la riserva.\n" +
                    "Il numero max di riserva è stato impostato al valore minimo di default: 2");
        else maxRiserva = tempVal;

        Set<Tag> chosenTags;
        while (true){
            chosenTags = impostaTags();
            if (chosenTags.isEmpty())
                view.message("Devi scegliere almeno un tag tra quelli elencati");
            else break;
        }

        view.message("Vuoi confermare la creazione dell'esperienza [Y/n]:");
        boolean accetta = view.fetchBool();

        if (accetta) {
            gestoreEsperienze.add(nomeE, descrizioneE, dI, dF, minP, maxP, percorso, costoIndividuale, maxRiserva, chosenTags);
            view.message("La creazione dell'esperienza è avvenuta con successo");
        } else {
            //  TODO: reset
            view.message("La creazione dell'esperienza è stata cancellata");
        }
    }

    private Set<Tag> impostaTags() {
        Set<Tag> tagsApprovati = gestoreTag.getTags(e -> e.getState().equals(TagStatus.APPROVATO));
        Set<String> viewSet = tagsApprovati.stream().map(Tag::getName).collect(Collectors.toSet());
        view.message("Scegli i tag da associare all'esperienza", viewSet);
        Set<String> viewSubSet = view.fetchSubSet(viewSet);
        Set<Tag> chosenTags = new HashSet<>();
        if (!viewSubSet.isEmpty())
            for (String tagName : viewSubSet)
                tagsApprovati.stream().filter(t -> t.getName().equals(tagName)).findFirst().ifPresent(chosenTags::add);
        return chosenTags;
    }
}
