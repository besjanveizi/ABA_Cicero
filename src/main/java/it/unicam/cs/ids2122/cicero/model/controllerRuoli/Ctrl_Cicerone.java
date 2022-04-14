package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.TipoFattura;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.gestori.GestoreEsperienze;
import it.unicam.cs.ids2122.cicero.model.gestori.GestorePercorso;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.gestori.GestoreTag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.model.services.ServiceFattura;
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
        gestoreEsperienze = GestoreEsperienze.getInstance(cicerone);
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

    private void cancellaEsperienza() {
        Set<Esperienza> esperienze = gestoreEsperienze
                .getAllEsperienze(e-> e.getStatus()== EsperienzaStatus.IDLE || e.getStatus()== EsperienzaStatus.VALIDA);
        if (esperienze.isEmpty()){
            view.message("Non ci sono esperienze da cancellare");
        }
        else {
            Esperienza e = selezionaEsperienza(esperienze);
            view.message(e.toString());
            Set<BeanPrenotazione> prenotazioni = gestoreEsperienze.getPrenotazioni(e);
            if (!prenotazioni.isEmpty()) {
                view.message("\nLa cancellazione dell'esperienza comporterà la cancellazione automatica " +
                        "(e rimborso se previsto) di " + prenotazioni.size() + " prenotazioni associate.\n");
            }
            int scelta = view.fetchChoice("1) Prosegui con la cancellazione\n2) Torna al menu principale",
                    2);
            if (scelta == 2) {
                view.message("L'esperienza non è stata cancellata");
            }
            else {
                gestoreEsperienze.cancellaEsperienza(e, prenotazioni);
                view.message("L'esperienza + " + e.getName() + " è stata cancellata");
            }
        }
    }

    private void proponiTag() {
        Set<Tag> allTags = gestoreTag.getTags(p -> true);
        String newTagName = "";
        boolean annulla = false;
        while (!annulla) {
            newTagName = view.ask("Inserisci il nome del tag da proporre");
            if (newTagName.equals(""))
                view.message("Il nome del tag non può essere vuoto");
            else {
                String finalNewTagName = newTagName;
                if(allTags.stream()
                        .anyMatch(t -> Objects.equals(t.getName(), finalNewTagName))) {
                    view.message("Il tag " + newTagName + " è già stato proposto o approvato");
                    int choice = view.fetchChoice("1) inserire un nuovo tag;\n2)torna al menu principale", 2);
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
                    view.message("La descrizione non può essere vuota");
                else break;
            }
            view.message("Confermare la proposta del tag? [Y,n]");
            if (view.fetchBool()) {
                gestoreTag.proponi(newTagName, descrizioneTag);
                view.message("Il tag è stato proposto");
            }
            else view.message("Proposta del tag annullata");
        }
        else view.message("Proposta del tag annullata");
    }

    /**
     * Permette di elaborare la creazione di una nuova <code>Esperienza</code>.
     */
    private void creaEsperienza() {
        LocalDateTime now = LocalDateTime.now();
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
            gestoreEsperienze.add(nomeE, cicerone, descrizioneE, dI, dF, minP, maxP, percorso,
                            costoIndividuale, maxRiserva, chosenTags);
            view.message("La creazione dell'esperienza è avvenuta con successo");
        } else {
            percorso.reset();
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

    private void impostaMenu() {
        menuItems.add("4) Crea Esperienza");
        menuItems.add("5) Proponi nuovo Tag");
        menuItems.add("6) Cancella Esperienza");
    }
}
