package view;

/**
 * Rappresenta l'interfaccia con cui un utente interagisce per poter svolgere attivit&agrave
 * inerenti al suo ruolo nel sistema.
 * @param <T> tipo dell'interazione che avviene tra l'utente e il sistema.
 */
public interface IView<T> {

    /**
     * Permette di inviare un messaggio all'utente <code>Cicerone</code> e di ottenere una risposta da esso.
     * @param message messaggio da mandare al <code>Cicerone</code>.
     * @return messaggio di risposta.
     */
    T ask(T message);

    /**
     * Permette di inviare un messaggio all'utente <code>Cicerone</code>.
     * @param message messaggio da inviare al <code>Cicerone</code>.
     */
    void message(T message);
}
