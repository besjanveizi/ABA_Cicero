package it.unicam.cs.ids2122.cicero.model.service.ricerca;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.IBacheca;
import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rappresenta il gestore del processo di ricerca di esperienze pubblicate sulla piattaforma Cicero
 */
public class GestoreRicerca {
    private final IBacheca bacheca;

    public GestoreRicerca(){
        bacheca = Bacheca.getInstance();
    }

    /**
     * Effettua la ricerca di esperienze applicando i filtri passati
     * @param filtroNome nome dell'esperienza
     * @param filtroTags tag associati all'esperienza
     * @param filtroAree toponimi associati all'esperienza
     * @return Set di esperienze trovate
     */
    public Set<Esperienza> ricerca(String filtroNome, Set<Tag> filtroTags, Set<Area> filtroAree){
        Set<Esperienza> allEsperienze = bacheca.getAllEsperienze();
        Set<Esperienza> esperienzeFiltrate = new HashSet<>();
        esperienzeFiltrate.addAll(filterByNome(allEsperienze,filtroNome));
        esperienzeFiltrate.addAll(filterByTags(esperienzeFiltrate,filtroTags));
        esperienzeFiltrate.addAll(filterByToponimi(esperienzeFiltrate,filtroAree));
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
