package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Toponimo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rappresenta l'interfaccia generale di un builder che si occupa di elaborare tutti i passi necessari per creare l'<code>Esperienza</code>.
 */
public interface BuilderEsperienza {

    /**
     * Permette di impostare il nome dell'<code>Esperienza</code>.
     * @param nomeEsperienza nome dell'<code>Esperienza</code>.
     */
    void setNome(String nomeEsperienza);

    /**
     * Permette di impostare la data d'inizio dell'<code>Esperienza</code>.
     * @param inizioEsperienza data d'inizio dell'<code>Esperienza</code>.
     */
    void setInizio(LocalDateTime inizioEsperienza);

    /**
     * Permette di impostare la data di fine dell'<code>Esperienza</code>.
     * @param fineEsperienza data di fine dell'<code>Esperienza</code>.
     */
    void setFine(LocalDateTime fineEsperienza);

    /**
     * Permette di impostare il numero massimo di partecipanti.
     * Si riferisce, quindi, al numero massimo dei posti prenotati all'<code>Esperienza</code>.
     * @param maxPartecipanti numero massimo dei partecipanti all'<code>Esperienza</code>.
     */
    void setMax(int maxPartecipanti);

    /**
     * Permette di impostare il numero minimo di partecipanti.
     * Si riferisce, quindi, al numero minimo dei posti prenotati perché un'<code>Esperienza</code> possa effettuarsi.
     * @param minPartecipanti numero minimo dei partecipanti all'<code>Esperienza</code>.
     */
    void setMin(int minPartecipanti);

    /**
     * Permette di impostare il numero massimo dei giorni di riserva di un posto all'<code>Esperienza</code>.
     * @param maxGiorni numero massimo dei giorni di riserva per l'<code>Esperienza</code>
     */
    void setGiorniRiserva(int maxGiorni);

    /**
     * Permette di impostare i toponimi dell'<code>Esperienza</code>.
     * @param toponimi lista dei toponimi relativi all'<code>Esperienza</code>.
     */
    void setToponimi(List<Toponimo> toponimi);

    /**
     * Permette di impostare i tags dell'<code>Esperienza</code>.
     * @param tags lista dei tag relativi all'<code>Esperienza</code>.
     */
    void setTags(List<Tag> tags);

    /**
     * Restituisce l'<code>Esperienza</code> creata con i parametri dei setter invocati.
     * @return l'<code>Esperienza</code> creata.
     */
    Esperienza getResult();

    /**
     * Permette di pulire i campi impostati dai setter.
     */
    void reset();
}
