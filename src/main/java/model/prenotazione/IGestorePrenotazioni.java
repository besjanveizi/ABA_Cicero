package model.prenotazione;

import java.util.List;

public interface IGestorePrenotazioni<E extends IProprietaEsperienza> {

    /**
     * crea un nuovo builder
     *
     * @param e l' esperienza
     * @return nuova istanza di un builder
     */
    BuilderPrenotazione prenotaEsperienza(E e);


    /**
     * salva una nuova esperienza.
     * prima aggiunge la prenotazione alla lista corrente.
     * Internamente chiamerà il DB per lavare la prenotazione
     *
     * @param prenotazione la prenotazione che si desidera inserire
     */
    void aggiungiPrenotazione(Prenotazione prenotazione);

    /**
     * Annulla la prenotazione di un esperienza, recuperando
     * la prenotazione attraverso l' indice selezionato
     *
     * @param indice indice dell' esperienza selezionata
     */
    void annullaPrenotazione(int indice);

    /**
     * "getter" della classe
     *
     * @return la lista corrente
     */
    List<Prenotazione> recuperaEsperienzeAssociate();

    /**
     * Se si desidera modificare un oggetto prenotazione
     * questo viene passato per la ricostruzione di un builder
     * e quindi può essere soggetto alle modifiche permesse dall' interfaccia
     *
     * @param objPrenotazione reference di un oggetto nella lista corrente
     * @return un builder con i parametri impostati
     */
    IBuilderInterface modificaPrenotazione(Prenotazione objPrenotazione);


    /**
     * gestisce la creazione di una prenotazione con invito.
     * come?
     * Associando all'interno del DB a degli utenti la stessa prenotazione.
     *
     * @param nomi_turisti i turisti
     */
    void prenotazioneConInvito(List<String> nomi_turisti);


    /**
     * stampa a video le attuali prenotazioni
     */
    void showAll();
}
