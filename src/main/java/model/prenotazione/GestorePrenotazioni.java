package model.prenotazione;


import model.ruoli.Turista;

import java.util.ArrayList;
import java.util.List;


/**
 * Note:  una qualsiasi cambiamento dell' utente e delle prenotazioni
 *  ad esso associate sarà scritto/sovrascritto su un determinato mezzo di
 *  memorizzazione persistente
 *
 * @param <E> Esperienza
 * @param <T> Turista
 */
public class GestorePrenotazioni<E extends IProprietaEsperienza,T extends Turista> implements IGestorePrenotazioni<E> {

    /**
     * il turista associato
     */
    private T turista_associato;

    private List<IObjPrenotazione> lista_prenotazioni;

    private IDB databaseInterface;

    /**
     * Ogni gestore prenotazione è associato ad un singolo turista
     * in relazione 1:1
     *
     * @param t turista
     * @param databaseInterface il database di riferimento
     */
    public GestorePrenotazioni(T t,IDB databaseInterface) {
        this.turista_associato = t;
        setUpListaPrenotazioni();
        this.databaseInterface = databaseInterface;
    }

    /**
     * recupera la lista delle prenotazioni dal DB
     */
    private void setUpListaPrenotazioni() {
        //... TODO DB
        if(lista_prenotazioni == null){
            lista_prenotazioni = new ArrayList<>();
        }
    }

    @Override
    public BuilderPrenotazione prenotaEsperienza(E e){
        return new BuilderPrenotazione(e);
    }

    @Override
    public void aggiungiPrenotazione(ObjPrenotazione prenotazione){
       lista_prenotazioni.add(prenotazione);
       salvaPrenotazione(prenotazione);
    }

    /**
     * Salvataggio nel DB
     *
     * @param prenotazione
     */
    private void salvaPrenotazione(ObjPrenotazione prenotazione){
        //.. TODO DB
        databaseInterface.inserisci(prenotazione);
        //..
    }


    @Override
    public void annullaPrenotazione(int indice){
       lista_prenotazioni.remove(indice);
       //.. TODO DB
    }

    @Override
    public List<IObjPrenotazione> recuperaEsperienzeAssociate(){
        return lista_prenotazioni;
    }

    @Override
    public IBuilderInterface modificaPrenotazione(IObjPrenotazione objPrenotazione){
        IBuilderInterface iBuilderInterface = new BuilderPrenotazione();
        return iBuilderInterface.reBuild(objPrenotazione);
    }

    @Override
    public void prenotazioneConInvito(List<String> nomi_turisti) {
        //...
    }


    @Override
    public void showAll(){
        lista_prenotazioni.stream().forEach(iObjPrenotazione -> System.out.println(iObjPrenotazione.toString()));
    }


}
