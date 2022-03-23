package it.unicam.cs.ids2122.cicero.model.prenotazione;

import java.util.List;

public interface IDB {

    /**
     * sovrascrittura
     *
     * @param args i parametri da inserire
     * @param <T> il tipo da inserire
     */
    <T> void inserisci(T ... args);

    /**
     * recupera una tabella
     *
     * @param <T> il tipo degli oggetti in lista
     * @return
     */
    <T>List<T> carica();


    /**
     *
     * confronta elementi presi dal DB con oggetti run time
     *
     * @param listaPerAggiornamento lista per il confronto
     * @param <T> il tipo
     */
    <T> void aggiorna(List<T> listaPerAggiornamento);




}
