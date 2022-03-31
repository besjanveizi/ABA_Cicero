package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;




import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.Invito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.PropInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.SimpleInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class GestoreInviti extends AbstractGestore{

    /**
     * lista degli inviti ricevuti
     */
    private List<Invito> lista_inviti;

    /**
     * query per l' inserimento di un invito
     */
    private String sql_insert = "INSERT INTO public.inviti (uid_mittente, id_esperienza, email_destinatario," +
            " data_creazione, data_scadenza_riserva, posti_riservati)" +
            " VALUES ( {0}, {1} , {2}, {3} , {4} , {5} );";

    /**
     * query per la verifica presenza di inviti
     */
    private String sql_check = "SELECT * FROM public.inviti WHERE email_destinatario=";


    /**
     *
     * @param utente_corrente utente loggato nel sistema
     * @param dbManager il db di riferimento
     * @throws SQLException in caso di errori
     */
    public GestoreInviti(Utente utente_corrente, DBManager dbManager) throws SQLException {
        super(utente_corrente, dbManager);
        lista_inviti = new ArrayList<>();
        recupera_inviti();
    }

    /**
     * crea un invito che verrà inserito nel db
     * @param esperienza l' esperienza selezionata
     * @param mail_invitato mail del destinatario
     */
    public void crea_invito(Esperienza esperienza, String mail_invitato, int posti_riservati){
        Invito invito = new SimpleInvito(utente_corrente.getID(), esperienza.getId(),
                mail_invitato, esperienza.getDataInizio(), esperienza.getMaxRiserva(), posti_riservati);
         String sql_format = MessageFormat.format(sql_insert, getToken(invito));
         dbManager.insert_update_delete_query(sql_format);
         lista_inviti.add(invito);
    }

    /**
     * Genera un token per l' inserimento
     * private String sql_insert = "INSERT INTO public.inviti (id_mittente, id_destinatario, id_esperienza, email_destinatario," +
     *             " data_creazione, data_scadenza_riserva, posti_riservati)" +
     *             " VALUES ( {0}, {1} , {2}, {3} , {4} , {5}  );";
     * @param invito da trasformare
     * @return
     */
    private Object[] getToken(Invito invito){
        PropInvito propInvito = (PropInvito) invito;
       Object[] token ={
               propInvito.getId_mittente(),
               propInvito.getId_esperienza(),
               "'"+propInvito.getEmail_destinatario()+"'",
               "'"+propInvito.getData_creazione().toString().replace('T', ' ')+"'",
               "'"+propInvito.getData_scadenza_riserva().toString().replace('T', ' ')+"'",
               propInvito.getPosti_riservati()
        };
       return token;
    }

    public void recupera_inviti() throws SQLException {
        ResultSet resultSet = dbManager.select_query(sql_check+utente_corrente.getMail()+";");
        new DBInvito().genera(resultSet, lista_inviti);
    }


    public Invito select_invito(int index) throws IndexOutOfBoundsException{
        return lista_inviti.get(index);
    }


    @Override
    public void show() {
        lista_inviti.stream().forEach(System.out::println);
    }



}
