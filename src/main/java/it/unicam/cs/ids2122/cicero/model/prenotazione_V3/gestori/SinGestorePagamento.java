package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori;

import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.SystemConstraints;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.Fattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.SimpleFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.StatoPagamento;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBFattura;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SinGestorePagamento {

    private static SinGestorePagamento sinGestorePagamento= null;

    private IUtente utente_corrente;

    private DBManager dbManager = DBManager.getInstance();


    private List<Fattura> lista_pagamenti_effettuati;
    private List<Fattura> lista_pagamenti_ricevuti;

    private String sql_insert = "INSERT INTO public.fatture (id_prenotazione, data_pagamento , id_client_origine, id_client_destinatario," +
            " posti_pagati, importo, valuta, stato_fattura) " +
            "VALUES ( {0} , {1} , {2} , {3} , {4} , {5} , {6} , {7} );";

    private String sql_select_effettuati= "SELECT * FROM public.fatture WHERE id_client_origine= {0} " ;

    private String sql_select_ricevuti= "SELECT * FROM public.fatture WHERE id_client_destinatario= {0} " ;


    private SinGestorePagamento(IUtente iUtente) throws SQLException {
        this.utente_corrente = iUtente;
        if(utente_corrente!=null) {
            lista_pagamenti_effettuati = new ArrayList<>();
            lista_pagamenti_ricevuti = new ArrayList<>();
            carica_pagamenti_effettuati();
            carica_pagamenti_ricevuti();
        }
    }

    public static SinGestorePagamento getInstance(IUtente iUtente) throws SQLException {
        if(sinGestorePagamento == null) {
            return new SinGestorePagamento(iUtente);
        }return sinGestorePagamento;
    }


    private Consumer<String> mapping(Fattura fattura){
        switch (fattura.getStatoPagamento()){
            case TURISTA_ADMIN: return fattura::turista_admin; // entry point  public void crea_fattura(Prenotazione prenotazione)
            case ADMIN_ADMIN: {
                if(utente_corrente == null){
                    // sta agendo il sistema
                    return fattura::admin_cicerone;
                }else {
                    return fattura::admin_turista;
                }
            }
            default:return null;
        }
    }

    private void carica_pagamenti_effettuati() throws SQLException {
        Object[] token = {"'"+SystemConstraints.id_client_generator(utente_corrente.getEmail())+"';" };
        String format = MessageFormat.format(sql_select_effettuati, token);
        ResultSet resultSet = dbManager.select_query(format);
        if(resultSet!=null) {
            new DBFattura().genera(resultSet, lista_pagamenti_effettuati);
            resultSet.close();
        }
    }

    private void carica_pagamenti_ricevuti() throws SQLException {
        Object[] token = {"'"+SystemConstraints.id_client_generator(utente_corrente.getEmail())+"';" };
        String format = MessageFormat.format(sql_select_ricevuti, token);
        ResultSet resultSet = dbManager.select_query(format);
        if(resultSet!=null){
            new DBFattura().genera(resultSet, lista_pagamenti_ricevuti);
            resultSet.close();
        }
    }

    /**
     *    "INSERT INTO public.fatture (id_prenotazione, data_pagamento , id_client_origine, id_client_destinatario," +
     *             " posti_pagati ,importo, valuta, stato_fattura ) " +
     *             "VALUES ( {0} , {1} , {2} , {3} , {4} , {5} , {6} , {7} );";
     * @param prenotazione
     * @throws SQLException
     */
    public void crea_fattura(Prenotazione prenotazione)  {
        Fattura fattura = new SimpleFattura(prenotazione.getID_prenotazione(), prenotazione.getPosti(),
                prenotazione.getPrezzo_totale(),
                prenotazione.getValuta());


        String id_client = null;
        if(utente_corrente==null){
            id_client =  SystemConstraints.id_client_generator(recupera_mail_autore(prenotazione));
        }else id_client = utente_corrente.getID_Client();

        mapping(fattura).accept(id_client);
        String format = MessageFormat.format(sql_insert, getToken(fattura));

        int id = dbManager.insert_update_delete_query(format);
        if(id!= -1){
            prenotazione.cambiaStatoPrenotazione(StatoPrenotazione.PAGATA);
            fattura.assegna_id(id);
            lista_pagamenti_effettuati.add(fattura);
        }
    }


    /**
     * recupera l' id dell' autore dell' esperienza relativa
     * @param prenotazione
     * @return id dell' autore
     * @throws SQLException
     */
    private String recupera_mail_autore(Prenotazione prenotazione){
        try {
            String sql = "SELECT uid_cicerone FROM public.esperienze WHERE id_esperienza=" + prenotazione.getID_esperienza() + ";";
            ResultSet rs = dbManager.select_query(sql);
            rs.next();
            int id = rs.getInt(1);
            String sql_u = "select email from public.utenti_registrati where uid=" + id + ";";
            ResultSet resultSet = dbManager.select_query(sql_u);
            resultSet.next();
            return resultSet.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * "INSERT INTO public.fatture (id_prenotazione, data_pagamento , id_client_origine, id_client_destinatario," +
     *             " posti_pagati, importo, valuta, stato_fattura ) " +
     *             "VALUES ( {0} , {1} , {2} , {3} , {4} , {5} , {6} , {7} );";
     * @param fattura
     * @return
     */
    private Object[] getToken(Fattura fattura){
        Object[] token = {
                fattura.getId_prenotazione(),
                "'"+fattura.getData_pagamento().toString().replace('T', ' ')+"'",
                "'"+fattura.getId_client_origine()+"'",
                "'"+fattura.getId_client_destinatario()+"'",
                fattura.getPosti_pagati(),
                "'"+fattura.getImporto()+"'",
                "'"+fattura.getValuta()+"'",
                fattura.getStatoPagamento().getCode()
        };
        return token;
    }

    public Fattura get_effettuati(int index) throws IndexOutOfBoundsException{
        return lista_pagamenti_effettuati.get(index);
    }
    public Fattura get_ricevuti(int index) throws IndexOutOfBoundsException{
        return lista_pagamenti_ricevuti.get(index);
    }


    public void show() {
        System.out.println("EFFETTUATI");
        lista_pagamenti_effettuati.stream().forEach(System.out::println);
        System.out.println("RICEVUTI");
        lista_pagamenti_ricevuti.stream().forEach(System.out::println);
    }



}
