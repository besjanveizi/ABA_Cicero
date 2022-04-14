package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.IBacheca;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;

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
        IBacheca bacheca = Bacheca.getInstance();
        Set<Esperienza> allEsperienze = bacheca.getEsperienze(e-> e.getStatus()== EsperienzaStatus.IDLE || e.getStatus()== EsperienzaStatus.VALIDA);

        if(filtroNome.isEmpty() && filtroAree.isEmpty() && filtroTags.isEmpty()) return allEsperienze;

        if(!filtroNome.isEmpty())
            allEsperienze.removeAll(filterByNome(allEsperienze, filtroNome));
        if(!filtroTags.isEmpty())
            allEsperienze.removeAll(filterByTags(allEsperienze, filtroTags));
        if(!filtroAree.isEmpty())
            allEsperienze.removeAll(filterByToponimi(allEsperienze,filtroAree));
        return allEsperienze;
    }

    private Set<Esperienza> filterByNome(Set<Esperienza> esperienze, String filtroNome){
        return esperienze.stream().filter(e -> !e.getName().contains(filtroNome)).collect(Collectors.toSet());
    }

    private Set<Esperienza> filterByTags(Set<Esperienza> esperienze, Set<Tag> filtroTags){
            return esperienze.stream().filter(e -> e.getTags().stream().noneMatch(filtroTags::contains)).collect(Collectors.toSet());
    }

    private Set<Esperienza> filterByToponimi(Set<Esperienza> esperienze, Set<Area> filtroAree){
        return esperienze.stream().filter(e -> e.getAree().stream().noneMatch(filtroAree::contains)).collect(Collectors.toSet());
    }
}
