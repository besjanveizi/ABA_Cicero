package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp {

    public static void main(String[] args) throws IOException {

        ProcessoDiSistema p = new ProcessoDiSistema();
        p.schedule();


        /*PostgresDB postgresDB = new PostgresDB("cicero","postgres","asdasd12345" );
        String pass = "asdasd12345";
        String hash_pass = String.valueOf(Math.abs(pass.hashCode()));
        postgresDB.insert_update_delete_query("INSERT INTO public.utenti_registrati(\n" +
                "\tuid, username, email, password, user_type)\n" +
                "\tVALUES (DEFAULT, 'admin', 'test@test.it', "+"'"+hash_pass+ "'," + "0);");
                delete from public.esperienze;
INSERT INTO public.esperienze(
	id_esperienza, uid_cicerone, nome, descrizione, data_pubblicazione, data_inizio, data_conclusione, data_termine, stato, max_partecipanti, min_partecipanti, costo_individuale, valuta, max_riserva, posti_disponibili)
	VALUES (default, 35 , 'test', 'Antartide', '1990-01-01 12:00', '1990-01-05 12:00', '1990-01-06 12:00', '1990-01-08 12:00',
			0, 10, 2, 9.99, 'EUR', 2, 10);

select * from public.esperienze;
         */

    }

}
