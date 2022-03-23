package it.unicam.cs.ids2122.cicero.model.service.ricerca;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.tag.GestoreTag;
import it.unicam.cs.ids2122.cicero.model.territorio.GestoreToponimi;
import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.tag.SimpleTag;
import it.unicam.cs.ids2122.cicero.model.territorio.Toponimo;

import java.util.ArrayList;
import java.util.List;

public class  GestoreRicerche  {
    private GestoreToponimi gestoreToponimi;
    private GestoreTag gestoreTags;
    private Bacheca bacheca;

    private List<SimpleTag> TagSelezionati;
    private List<Toponimo> ToponimiSelezionati;

    public GestoreRicerche(){
        TagSelezionati=new ArrayList<>();
        ToponimiSelezionati=new ArrayList<>();
    }

    public GestoreRicerche(GestoreTag gestoreTags, GestoreToponimi gestoreToponimi){
        TagSelezionati=new ArrayList<>();
        ToponimiSelezionati=new ArrayList<>();
        this.gestoreTags=gestoreTags;
        this.gestoreToponimi=gestoreToponimi;
    }

    /**
     * Imposta la lista dei tag selezionati
     * @param tagSelezionati lista di tag con cui filtrare le esperienze
     */
    public void setTagSelezionati(List<SimpleTag> tagSelezionati) {
        TagSelezionati = tagSelezionati;
    }

    /**
     * Imposta la lista dei toponimi selezionati
     * @param toponimiSelezionati lista di {@link Toponimo} con cui filtrare le esperienze
     */
    public void setToponimiSelezionati(List<Toponimo> toponimiSelezionati) {
        ToponimiSelezionati = toponimiSelezionati;
    }


    /**
     * Genera e restituisce una lista contenente tutte le {@link Esperienza} che contengono tutti i Tag scelti dall'utente e che si trova in uno dei {@link Toponimo} definiti dall'utente.
     * @return Lista di esperienze
     */
    public List<Esperienza> ricercaEsperienza(){
        List<Esperienza> allEsperienze=bacheca.getAllEsperienze();
        List<Esperienza> ret=new ArrayList<>();
        for(Esperienza e:allEsperienze){
            if(ToponimiSelezionati.isEmpty()){
                if(TagSelezionati.isEmpty()){
                    //non sono stati selezionati tag o toponimi
                    ret.add(e);
                }else{
                    //non sono stati selezionati toponimi, ma sono stati selezionati tag
                    if(checkTags(e,TagSelezionati))ret.add(e);
                }
            }else{
                if(TagSelezionati.isEmpty()){
                    //sono stati selezionati toponimi, ma non sono stati selezionati tag
                    if(checkToponimi(e,ToponimiSelezionati))ret.add(e);
                }else{
                    //sono stati selezionati toponimi e tag
                    if(checkToponimi(e,ToponimiSelezionati) || checkTags(e,TagSelezionati))ret.add(e);
                }
            }
        }
        return ret;
    }

    /**
     * Ottiene dalla {@link Bacheca} tutte le esperienze disponibili
     * @return lista di tutte le esperienze presenti
     */
    public List<Esperienza> getAllEsperienze(){
        return bacheca.getAllEsperienze();
    }

    /**
     * Controlla che l'esperienza passata contenga tra i suoi tag, almeno uno di quelli selezionati
     * @param e Esperienza da controllare
     * @param tagSelezionati  Tag da ricercare all'interno dei tag dell'esperienza
     * @return  true se all'Esperienza è associato almeno uno dei tag passati
     */
    private boolean checkTags(Esperienza e, List<SimpleTag> tagSelezionati){
        for(SimpleTag t:tagSelezionati){
            if (e.getTagsAssociati().contains(t)) return true;
        }
        return false;
    }

    /**
     * Controlla che l'esperienza passata contenga tra i suoi toponimi, almeno uno di quelli selezionati
     * @param e Esperienza da controllare
     * @param toponimiSelezionati Toponimi da ricercare all'interno dei tag dell'esperienza
     * @return true se all'Esperienza è associato almeno uno dei toponimi passati
     */
    private boolean checkToponimi(Esperienza e, List<Toponimo> toponimiSelezionati){
        for(Toponimo t:toponimiSelezionati){
            if (e.getToponimiAssociati().contains(t)) return true;
        }
        return false;
    }

    /**
     *
     * @return lista di tutti i toponimi approvati sulla piattaforma
     */
    public List<Toponimo> getToponimi(){
        return gestoreToponimi.getAllToponimi();
    }

    /**
     *
     * @return lista di tutti i Tag approvati sulla piattaforma
     */
    public List<SimpleTag> getTags(){
        return gestoreTags.getAllTags();
    }

    /**
     * pulisce la lista contenente i toponimi selezionati
     */
    public void clearToponimiSelezionati(){
        ToponimiSelezionati.clear();
    }

    /**
     * pulisce la lista contenente i tag selezionati
     */
    public void clearTagSelezionati(){
        TagSelezionati.clear();
    }
}
