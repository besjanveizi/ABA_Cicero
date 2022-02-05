package model.prenotazione;


/**
 * Faced per prenotazione
 * parametri nascosti:
 * - data di creazione della prenotazione. -> recuperata dal sistema
 * - durata della validità della prenotazione. -> recuperata dai campi esperienza
 * - termine ultimo di validità della prenotazione. -> recuperata dai campi dell' esperienza
 *
 * @param <T> Turista non implementati
 */
public interface IBuilderInterface<T>  {



        /**
         * Definisce il numero di posti associato alla prenotazione
         *
         * @param posti
         * @return il builder
         */
        IBuilderInterface buildPostiPrenotati(int posti, IGestoreDisponibilita disponibilita) throws BuilderRunTimeException;

        /**
         * definisce se è una prenotazione a tempo limitato,
         * aka -> Riserva
         *
         * @param b default false
         * @return il builder
         */
        IBuilderInterface buildRiserva(boolean b);

        /**
         * flag per la possibilità di invitare
         * altri turisti
         *
         * @param b se è o meno un invito
         * @return il builder
         */
        IBuilderInterface buildInvito(boolean b);

        /**
         * recupera il risultato della creazione step-by-step
         * Creazione di un nuovo oggetto ma con controllo.
         * Utilizzo reflection per prelevare i campi dell' oggetto
         * modificare l'accesso, controllare lo stato, reimpostare i modificatori
         * e contare i valori null. Se sono più di 0 torna un elemento null
         * (eliminando se stessa).
         *
         * @return la prenotazione compilata correttamente, null altrimenti.
         */
        IBuilderInterface getResult();


        /**
         * recupera le informazioni da una prenotazione,
         * nel caso sia necessaria una modifica
         *
         * @param objPrenotazione una prenotazione completa
         * @return il builder di una prenotazione già creata
         */
        IBuilderInterface reBuild(IObjPrenotazione objPrenotazione);



}
