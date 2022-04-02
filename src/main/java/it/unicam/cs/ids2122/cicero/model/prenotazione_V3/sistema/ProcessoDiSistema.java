package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;

import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBPrenotazione;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class ProcessoDiSistema{


    private volatile SynchronousQueue<Prenotazione> controllati;

    public void ProcessoDiSistema(){
        List<Prenotazione> temp = new ArrayList<>();
        try {
            new DBPrenotazione().genera(DBManager.getInstance().select_query("select * from public.prenotazioni;"), temp);
        } catch (SQLException e) {
            e.printStackTrace();
         }
          this.controllati = (SynchronousQueue<Prenotazione>) Collections.synchronizedList(temp);
    }

    void sys_check(){
        LocalDateTime now = LocalDateTime.now();
        this.controllati
                .stream()
                .filter(prenotazione -> prenotazione.check_is_before(now))
                .forEach(prenotazione -> prenotazione.cambiaStatoPrenotazione(StatoPrenotazione.CANCELLATA));
    }


}
