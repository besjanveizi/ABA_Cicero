package it.unicam.cs.ids2122.cicero.model.esperienza.percorso;

import it.unicam.cs.ids2122.cicero.model.territorio.GestoreAree;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GestorePercorso {
    private Set<Area> aree;
    private IView<String> view;
    private Percorso percorso;

    public GestorePercorso(IView<String> view) {
        this.view = view;
        this.aree = GestoreAree.getInstance().getAree();
        this.percorso = new Percorso();
    }

    public Percorso creaPercorso() {
        Tappa partenza = creaTappa();
        while (true) {
            String infoSpostamento = view.ask("Descrivi lo spostamento alla prossima tappa:");
            Tappa destinazione = creaTappa();
            percorso.addSpostamento(partenza, destinazione, infoSpostamento);
            view.message("Vuoi inserire una tappa successiva? [Y, n]");
            if (!view.fetchBool()) break;
            else partenza = destinazione;
        }
        return percorso;
    }

    private Tappa creaTappa() {
        Area area = impostaArea();
        String info = view.ask("Inserisci ulteriori informazioni riguardo la tappa (es. via, piazza, vicino a.., etc.)");
        Tappa tappa = new Tappa(area, info);
        String nomeAttivita, descrizioneAttivita;
        int i = 1;
        while (true) {
            while (true) {
                nomeAttivita = view.ask("Inserisci il nome della " + i + "° attività della tappa");
                if (nomeAttivita.isBlank())
                    view.message("Il nome inserito non è valido");
                else break;
            }
            descrizioneAttivita = view.ask("Inserisci una descrizione per la " + i + "° attività della tappa");
            tappa.addAttivita(nomeAttivita, descrizioneAttivita);
            view.message("Vuoi inserire una nuova attività per la tappa?");
            if (view.fetchBool()) i++;
            else break;
        }
        return tappa;
    }

    private Area impostaArea() {
        Set<String> viewSet = aree.stream().map(Area::getToponimo).collect(Collectors.toSet());
        view.message("Seleziona il toponimo per la nuova tappa", viewSet);
        String toponimo = view.fetchSingleChoice(viewSet);
        return aree.stream().filter(e -> Objects.equals(e.getToponimo(), toponimo)).findFirst().orElse(null);
    }
}