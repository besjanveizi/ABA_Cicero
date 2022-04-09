package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;


import it.unicam.cs.ids2122.cicero.model.entities.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.services.ServiceDisponibilita;

import java.sql.SQLException;

public final class SinGestoreDisponibilita {

    private static SinGestoreDisponibilita sinGestoreDisponibilita = null;

    private SinGestoreDisponibilita() { }

    public static SinGestoreDisponibilita getInstance() {
        if(sinGestoreDisponibilita==null){
            sinGestoreDisponibilita = new SinGestoreDisponibilita();
        }return sinGestoreDisponibilita;
    }

    /**
     * In caso di annullamento di una prenotazione, restituisce all' esperienza i posti disponibili
     * @param prenotazione la prenotazione da annullare
     * @throws SQLException
     */
    public void modificaDisponibilita(BeanPrenotazione prenotazione)  {
        int posti_disponibili = ServiceDisponibilita.getInstance().select(prenotazione.getID_esperienza());
        ServiceDisponibilita.getInstance().update(posti_disponibili+prenotazione.getPosti(),prenotazione.getID_esperienza());
    }

    /**
     * utilizzato durante la fase di prenotazione aggiorna l' esperienza runtime e sul db
     * @param esperienza
     * @param num_posti
     */
    public void modificaDisponibilita(IEsperienza esperienza, int num_posti){
        esperienza.info().cambiaPostiDisponibili(-num_posti);
        int nuova_dips = esperienza.info().getPostiDisponibili() - num_posti;
        ServiceDisponibilita.getInstance().update(nuova_dips, esperienza.getId());
    }

    /**
     *recupera i posti disponibili di un esperienza
     * @param esperienza
     * @return
     * @throws SQLException
     */
    public int getPostiDisponibiliToDB(IEsperienza esperienza)  {
        return ServiceDisponibilita.getInstance().select(esperienza.getId());
    }




}
