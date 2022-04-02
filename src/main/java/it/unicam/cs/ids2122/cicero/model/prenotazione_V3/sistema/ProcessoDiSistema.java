package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;

import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBPrenotazione;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessoDiSistema implements Runnable{


    private ScheduledExecutorService service;

    private volatile List<Prenotazione> controllati;


    public ProcessoDiSistema(){
        List<Prenotazione> temp = new ArrayList<>();
        try {
            new DBPrenotazione().genera(DBManager.getInstance().select_query("select * from public.prenotazioni;"), temp);
        } catch (SQLException e) {
            e.printStackTrace();
         }
          controllati =Collections.synchronizedList(temp);
          service = Executors.newScheduledThreadPool(1);
    }

    void sys_check(){
        Logger.getAnonymousLogger().log(Level.INFO,"START");
        LocalDateTime now = LocalDateTime.now();
        this.controllati
                .stream()
                .filter(prenotazione -> prenotazione.check_is_before(now))
                .forEach(prenotazione -> prenotazione.cambiaStatoPrenotazione(StatoPrenotazione.CANCELLATA));
    }


    @Override
    public void run() {
        sys_check();
    }

    public  void schedule(){
        service.scheduleAtFixedRate(this, 0, 10,TimeUnit.MINUTES );
    }

}
