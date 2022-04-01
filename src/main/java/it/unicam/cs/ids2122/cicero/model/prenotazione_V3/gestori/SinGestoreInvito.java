package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.invito.Invito;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.invito.SimpleInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti.Utente;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SinGestoreInvito {

    private static SinGestoreInvito sinGestorePagamento= null;

    private IUtente utente_corrente;

    private DBManager dbManager = DBManager.getInstance();

    /**
     * lista degli inviti ricevuti
     */
    private List<Invito> lista_inviti;

    private List<Invito> lista_ricevuti;

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


    private SinGestoreInvito(IUtente utente_corrente)throws SQLException{
        this.utente_corrente = utente_corrente;
        lista_inviti = new ArrayList<>();
        lista_ricevuti = new ArrayList<>();
        recupera_inviti();
    }

    public static SinGestoreInvito getInstance(IUtente iUtente) throws SQLException {
        if(sinGestorePagamento == null){
            return new SinGestoreInvito(iUtente);
        }return sinGestorePagamento;
    }

    /**
     * crea un invito che verrà inserito nel db
     * @param esperienza l' esperienza selezionata
     * @param mail_invitato mail del destinatario
     */
    public void crea_invito(Esperienza esperienza, String mail_invitato, int posti_riservati){
        Invito invito = new SimpleInvito(
                utente_corrente.getID(),
                esperienza.getId(),
                mail_invitato,
                esperienza.getDataInizio(),
                esperienza.getMaxRiserva(),
                posti_riservati);
        String sql_format = MessageFormat.format(sql_insert, getToken(invito));
        if(dbManager.insert_update_delete_query(sql_format)!=-1){
            lista_inviti.add(invito);
        }
    }

    /**
     * Genera un token per l' inserimento
     * private String sql_insert = "INSERT INTO public.inviti (uid_mittente, id_destinatario, id_esperienza, email_destinatario," +
     *             " data_creazione, data_scadenza_riserva, posti_riservati)" +
     *             " VALUES ( {0}, {1} , {2}, {3} , {4} , {5}  );";
     * @param invito da trasformare
     * @return
     */
    private Object[] getToken(Invito invito){
        Object[] token ={
                invito.getId_mittente(),
                invito.getId_esperienza(),
                "'"+invito.getEmail_destinatario()+"'",
                "'"+invito.getData_creazione().toString().replace('T', ' ')+"'",
                "'"+invito.getData_scadenza_riserva().toString().replace('T', ' ')+"'",
                invito.getPosti_riservati()
        };
        return token;
    }

    public void recupera_inviti() throws SQLException {
        ResultSet resultSet = dbManager.select_query(sql_check+"'"+utente_corrente.getEmail()+"';");
        if(resultSet!= null){
            new DBInvito().genera(resultSet, lista_ricevuti);
            resultSet.close();
        }
    }

    public boolean notifica(){
        return lista_ricevuti.size() > 0;
    }

    public Invito select_invito(int index) throws IndexOutOfBoundsException{
        return lista_ricevuti.get(index);
    }

    public void show() {
        System.out.println("RICEVUTI:");
        lista_ricevuti.stream().forEach(System.out::println);
        System.out.println("SPEDITI");
        lista_inviti.stream().forEach(System.out::println);
    }


}
