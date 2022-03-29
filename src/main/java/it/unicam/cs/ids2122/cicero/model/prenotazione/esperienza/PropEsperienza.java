package it.unicam.cs.ids2122.cicero.model.prenotazione.esperienza;



import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Toponimo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


public interface PropEsperienza extends Esperienza {


    /**
     * Restituisce l'identificativo dell'<code>Esperienza</code>.
     * @return identificativo dell'<code>Esperienza</code>.
     */
    int getId();

    /**
     * Restituisce il nome dell'<code>Esperienza</code>.
     * @return nome dell'<code>Esperienza</code>.
     */
    String getName();

    /**
     * @return set di {@link Tag} associati all'esperienza
     */
    Set<Tag> getTagsAssociati();

    /**
     * @return set di {@link Toponimo} associati all'esperienza
     */
    Set<Toponimo> getToponimiAssociati();

    /* aggiunte */

    /**
     * @return id del cicerone
     */
    int getAutoreID();

    LocalDateTime getDataInizio();

    LocalDateTime getDataConclusione();

    LocalDateTime getDataPubblicazione();

    LocalDateTime getDataTerminazione();

    int getMaxPartecipanti();

    int getMinPartecipanti();

    int getMaxGiorniRiserva();

    int getPostiDisponibili();

    BigDecimal getPrezzo();

    StatoEsperienza getStato();

    String getValuta();


}
