package controller;

import model.GestoreRicerche;
import model.esperienza.Esperienza;
import model.esperienza.SimpleEsperienza;
import model.esperienza.SimpleTag;
import model.esperienza.Toponimo;
import view.IOricerca;

import java.util.ArrayList;
import java.util.List;

public class BoundaryRicerca {
    private GestoreRicerche gestoreRicerca;
    private List<Esperienza> esperienzeFiltrate;
    private IOricerca io;

    public BoundaryRicerca(){
        gestoreRicerca=new GestoreRicerche();
        esperienzeFiltrate=new ArrayList<>();
        io=new IOricerca();
    }

    /**
     * Gestisce tutto il processo di ricerca delle esperienze.
     * I tag ed i toponimi vengono mostrati all'utente, che puo' decidere di sceglierne alcuni con cui filtrare la ricerca.
     * Verra' infine mostrata la lista di esperienze trovate.
     */
    public void ricerca(){
        //clear lista
        io.startRicerca();
        gestoreRicerca.setToponimiSelezionati(selezionaToponimi());
        gestoreRicerca.setTagSelezionati(selezionaTag());
        esperienzeFiltrate.addAll(gestoreRicerca.ricercaEsperienza());
        io.showOrderedList(esperienzeFiltrate,"Esperienze trovate:");
        //qualcosa
    }

    /**
     * Permette all'utente di scegliere da una lista ordinata i toponimi con cui filtrare la ricerca di esperienze, salvando in memoria la selezione.
     */
    private List<Toponimo> selezionaToponimi(){
        List<Toponimo> allToponimi=gestoreRicerca.getToponimi();
        List<Toponimo> selezionati=new ArrayList<>();
        io.showOrderedList(allToponimi,"Toponimi:");
        for (int index:io.readIndexes()){
            selezionati.add(allToponimi.get(index));
        }
        return selezionati;
    }
    /**
     * Permette all'utente di scegliere da una lista ordinata i tag con cui filtrare la ricerca di esperienze, salvando in memoria la selezione.
     */
    private List<SimpleTag>  selezionaTag(){
        List<SimpleTag> allTags=gestoreRicerca.getTags();
        List<SimpleTag> selezionati=new ArrayList<>();
        io.showOrderedList(allTags,"Tags:");
        for (int index:io.readIndexes()){
            selezionati.add(allTags.get(index));
        }
        return selezionati;
    }

    /**
     * Mostra i dettagli relativi all'esperienza passata come parametro
     * @param esperienza l'esperienza di cui si vogliono conoscere i dettagli
     */
    public void showEsperienza(SimpleEsperienza esperienza){
        io.showEsperienza(esperienza);
    }

    /**
     * Mostra tutte le esperienze presenti sulla piattaforma
     */
    public void showAllEsperienze(){
        io.showOrderedList(gestoreRicerca.getAllEsperienze(),"Esperienze disponibili:");
    }


}
