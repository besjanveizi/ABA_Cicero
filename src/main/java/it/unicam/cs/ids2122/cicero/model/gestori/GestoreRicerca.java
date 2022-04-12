package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.IBacheca;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rappresenta il gestore del processo di ricerca di esperienze pubblicate sulla piattaforma Cicero
 */
public class GestoreRicerca {

    public GestoreRicerca(){ }

    /**
     * Effettua la ricerca di esperienze applicando i filtri passati
     * @param filtroNome nome dell'esperienza
     * @param filtroTags tag associati all'esperienza
     * @param filtroAree toponimi associati all'esperienza
     * @return Set di esperienze trovate
     */
    public Set<Esperienza> ricerca(String filtroNome, Set<Tag> filtroTags, Set<Area> filtroAree){
        Bacheca bacheca = Bacheca.getInstance();
        Set<Esperienza> allEsperienze = bacheca.getAllEsperienze();
        Set<Esperienza> esperienzeFiltrate = new HashSet<>();
        if(filtroNome.isEmpty() && filtroAree.isEmpty() && filtroTags.isEmpty())return allEsperienze;
        if(!filtroNome.isEmpty()) esperienzeFiltrate.addAll(filterByNome(allEsperienze,filtroNome));
        if(!filtroTags.isEmpty()) esperienzeFiltrate.addAll(filterByTags(allEsperienze,filtroTags));
        if(!filtroAree.isEmpty()) esperienzeFiltrate.addAll(filterByToponimi(allEsperienze,filtroAree));
        return esperienzeFiltrate;
    }

    private Set<Esperienza> filterByNome(Set<Esperienza> esperienze, String filtroNome){
        return esperienze.stream().filter(e -> e.getName().contains(filtroNome)).collect(Collectors.toSet());
    }

    private Set<Esperienza> filterByTags(Set<Esperienza> esperienze, Set<Tag> filtroTags){
            return esperienze.stream().filter(e -> e.getTags().stream().anyMatch(filtroTags::contains)).collect(Collectors.toSet());
    }

    private Set<Esperienza> filterByToponimi(Set<Esperienza> esperienze, Set<Area> filtroAree){
        return esperienze.stream().filter(e -> e.getAree().stream().anyMatch(filtroAree::contains)).collect(Collectors.toSet());
    }
}
