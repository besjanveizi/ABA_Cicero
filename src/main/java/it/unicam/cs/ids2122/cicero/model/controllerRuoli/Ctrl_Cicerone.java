package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;
import it.unicam.cs.ids2122.cicero.model.gestori.*;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Rappresenta un gestore radice un utente <code>Cicerone</code> che elabora le sue interazioni con il sistema.
 */
public class Ctrl_Cicerone extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    private final GestorePercorso gestorePercorso;
    private final GestoreEsperienze gestoreEsperienze;
    private final Cicerone cicerone;
    private final GestoreTag gestoreTag;

    public Ctrl_Cicerone(Cicerone cicerone) {
        super(cicerone);
        this.cicerone = cicerone;
        impostaMenu();
        gestoreEsperienze = new GestoreEsperienze(cicerone);
        gestoreTag = GestoreTag.getInstance();
        gestorePercorso = new GestorePercorso(view);
    }

    @Override
    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        switch (scelta) {
            case 4:
                creaEsperienza();
                break;
            case 5:
                proponiTag();
                break;
            case 6:
                cancellaEsperienza();
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    /**
     * Permette di elaborare la creazione di una nuova <code>Esperienza</code>.
     */
    private void creaEsperienza() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        String nomeE = view.ask("Inserisci il nome della nuova esperienza:");
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
            if (maxP >= minP) break;
            else view.message("ERRORE: Il numero massimo di partecipanti deve essere maggiore o uguale del minimo inserito");
        }

        Percorso percorso = gestorePercorso.creaPercorso();

        view.message("Inserisci il costo individuale dell'esperienza");
        Money costoIndividuale = view.fetchMoney();

        int maxRiserva = 2;
        view.message("Imposta numero max di giorni riserva (minimo 2)");
        int tempVal = view.fetchInt();
        if (tempVal < 2)
            view.message("Hai inserito un valore sotto alla soglia minima per la riserva.\n" +
                    "Il numero max di riserva ?? stato impostato al valore minimo di default: 2");
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
            gestoreEsperienze.add(nomeE, cicerone, descrizioneE, dI, dF, minP, maxP, percorso,
                    costoIndividuale, maxRiserva, chosenTags);
            view.message("La creazione dell'esperienza ?? avvenuta con successo");
        } else {
            percorso.reset();
            view.message("La creazione dell'esperienza ?? stata cancellata");
        }

    }

    private void proponiTag() {
        Set<Tag> allTags = gestoreTag.getTags(p -> true);
        String newTagName = "";
        boolean annulla = false;
        while (!annulla) {
            newTagName = view.ask("Inserisci il nome del tag da proporre");
            if (newTagName.equals(""))
                view.message("Il nome del tag non pu?? essere vuoto");
            else {
                String finalNewTagName = newTagName;
                Tag foundTag = allTags.stream().filter(t -> t.getName().equals(finalNewTagName)).findFirst().orElse(null);
                if (foundTag != null) {
                    switch (foundTag.getState()) {
                        case PROPOSTO:
                            view.message("\nIl tag " + newTagName + " ?? gi?? stato proposto");
                            break;
                        case APPROVATO:
                            view.message("\nIl tag " + newTagName + " ?? gi?? stato approvato");
                            break;
                        case RIFIUTATO:
                            view.message("\nIl tag " + newTagName + " ?? gi?? stato rifiutato");
                            break;
                    }
                    int choice = view.fetchChoice("1) Inserire un nuovo tag;\n2) Torna al menu principale", 2);
                    if (choice == 2) annulla = true;
                }
                else break;
            }
        }
        String descrizioneTag;
        if (!annulla) {
            while (true) {
                descrizioneTag = view.ask("Inserisci la descrizione del tag " + newTagName);
                if (descrizioneTag.equals(""))
                    view.message("La descrizione non pu?? essere vuota");
                else break;
            }
            view.message("Confermare la proposta del tag? [Y,n]");
            if (view.fetchBool()) {
                gestoreTag.proponi(newTagName, descrizioneTag);
                view.message("Il tag ?? stato proposto");
            }
            else view.message("Proposta del tag annullata");
        }
        else view.message("Proposta del tag annullata");
    }

    private void cancellaEsperienza() {
        Set<Esperienza> esperienze = gestoreEsperienze
                .getAllEsperienze(e-> e.getStatus()== EsperienzaStatus.IDLE || e.getStatus()== EsperienzaStatus.VALIDA);
        Esperienza e = selezionaEsperienza(esperienze);
        if (e == null){
            view.message("Non ci sono esperienze da cancellare");
        }
        else {
            view.message(e.toString());
            Set<BeanPrenotazione> prenotazioni = gestoreEsperienze.getPrenotazioni(e, p -> !p.getStatoPrenotazione().equals(StatoPrenotazione.CANCELLATA));
            Set<BeanInvito> inviti = gestoreEsperienze.getInviti(e);
            if (!prenotazioni.isEmpty() || !inviti.isEmpty()) {
                view.message("\nLa cancellazione dell'esperienza comporter?? la cancellazione automatica " +
                        "(e rimborso se previsto) di " + prenotazioni.size() + " prenotazioni e "  + inviti.size() +
                        " inviti associati.\n");
            }
            int scelta = view.fetchChoice("1) Prosegui con la cancellazione\n2) Torna al menu principale",
                    2);
            if (scelta == 2) {
                view.message("L'esperienza non ?? stata cancellata");
            }
            else {
                gestoreEsperienze.cancellaEsperienza(e, prenotazioni, inviti);
                view.message("L'esperienza '" + e.getName() + "' ?? stata cancellata");
            }
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

    private void impostaMenu() {
        menuItems.add("4) Crea Esperienza");
        menuItems.add("5) Proponi nuovo Tag");
        menuItems.add("6) Cancella Esperienza");
    }
}
