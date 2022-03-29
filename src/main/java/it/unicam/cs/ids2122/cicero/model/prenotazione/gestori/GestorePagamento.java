package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;




import it.unicam.cs.ids2122.cicero.model.prenotazione.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.PropPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.SystemConstraints;
import it.unicam.cs.ids2122.cicero.model.prenotazione.pagamento.Fattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.pagamento.FunFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.pagamento.PropFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.pagamento.SimpleFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GestorePagamento extends AbstractGestore{

    private List<Fattura> lista_pagamenti_effettuati;
    private List<Fattura> lista_pagamenti_ricevuti;


    private String sql_insert = "INSERT INTO public.fatture (id_prenotazione, data_pagamento , id_client_origine, id_client_destinatario," +
            " posti_pagati, importo, valuta, stato_fattura ) " +
            "VALUES ( {0} , {1} , {2} , {3} , {4} , {5} , {6} , {7} );";

    private String sql_select_effettuati= "SELECT  * FROM public.fatture WHERE id_origine= {0} AND conto_origine= {1} " ;

    private String sql_select_ricevuti= "SELECT  * FROM public.fatture WHERE id_origine= {0} AND conto_destinatario= {1} " ;

    public GestorePagamento(Utente utente_corrente, DBManager dbManager) throws SQLException {
        super(utente_corrente, dbManager);
        lista_pagamenti_effettuati = new ArrayList<>();
        lista_pagamenti_ricevuti = new ArrayList<>();
        carica_pagamenti_effettuati();
        carica_pagamenti_ricevuti();
    }



    private Consumer<String> mapping(Fattura fattura){
        switch (((PropFattura) fattura).getStatoPagamento()){
            case TURISTA_ADMIN: return s -> ((FunFattura) fattura).turista_admin(s);
            case ADMIN_CICERONE: return s -> ((FunFattura) fattura).admin_cicerone(s);
            case ADMIN_TURISTA: return s -> ((FunFattura) fattura).admin_turista(s);
            default: return null;
        }
    }


    private void carica_pagamenti_effettuati() throws SQLException {
         Object[] token = { "'"+utente_corrente.getID()+"'", "'"+SystemConstraints.id_client_generator(utente_corrente.getMail())+"';" };
         String format = MessageFormat.format(sql_select_effettuati, token);
         ResultSet resultSet = dbManager.select_query(format);
         new DBFattura().genera(resultSet, lista_pagamenti_effettuati);
    }

    private void carica_pagamenti_ricevuti() throws SQLException {
        Object[] token = { "'"+utente_corrente.getID()+"'", "'"+SystemConstraints.id_client_generator(utente_corrente.getMail())+"';" };
        String format = MessageFormat.format(sql_select_ricevuti, token);
        ResultSet resultSet = dbManager.select_query(format);
        new DBFattura().genera(resultSet, lista_pagamenti_ricevuti);
    }

    /**
     *    "INSERT INTO public.fatture (id_prenotazione, data_pagamento , id_client_origine, id_client_destinatario," +
     *             " posti_pagati ,importo, valuta, stato_fattura ) " +
     *             "VALUES ( {0} , {1} , {2} , {3} , {4} , {5} , {6} , {7} );";
     * @param prenotazione
     * @throws SQLException
     */
    public void crea_fattura(Prenotazione prenotazione) throws SQLException {
        PropPrenotazione propPrenotazione = (PropPrenotazione) prenotazione;
        Fattura fattura = new SimpleFattura(propPrenotazione.getID_prenotazione(),
                propPrenotazione.getPosti(), propPrenotazione.getPrezzo_totale(), propPrenotazione.getValuta());
        mapping(fattura).accept(SystemConstraints.id_client_generator(utente_corrente.getMail())); // --> aggiorna la fattura creata
        String format = MessageFormat.format(sql_insert, getToken(fattura));
        dbManager.insert_update_delete_query(format);
        lista_pagamenti_effettuati.add(fattura);
    }

    /**
     * recupera l' id dell' autore dell' esperienza relativa
     * @param prenotazione
     * @return id dell' autore
     * @throws SQLException
     */
    private int recupera_id_autore(Prenotazione prenotazione) throws SQLException {
        String sql = "SELECT id_cicerone FROM public.esperienze WHERE id_esperienza="+ ((PropPrenotazione) prenotazione).getID_esperienza()+";";
        ResultSet rs = dbManager.select_query(sql);
        rs.next();
        return rs.getInt(1);
    }

    /**
     * "INSERT INTO public.fatture (id_prenotazione, data_pagamento , id_client_origine, id_client_destinatario," +
     *             " posti_pagati, importo, valuta, stato_fattura ) " +
     *             "VALUES ( {0} , {1} , {2} , {3} , {4} , {5} , {6} , {7} );";
     * @param fattura
     * @return
     */
    private Object[] getToken(Fattura fattura){
        PropFattura propFattura = (PropFattura) fattura;
        Object[] token = {
            propFattura.getId_prenotazione(),
               "'"+propFattura.getData_pagamento().toString().replace('T', ' ')+"'",
               "'"+propFattura.getId_client_origine()+"'",
                "'"+propFattura.getId_client_destinatario()+"'",
                propFattura.getPosti_pagati(),
                propFattura.getImporto(),
                "'"+propFattura.getValuta()+"'",
                propFattura.getStatoPagamento().getI()
        };
        return token;
    }




    public Fattura get_effettuati(int index) throws IndexOutOfBoundsException{
        return lista_pagamenti_effettuati.get(index);
    }
    public Fattura get_ricevuti(int index) throws IndexOutOfBoundsException{
        return lista_pagamenti_ricevuti.get(index);
    }

    @Override
    public void show() {
        System.out.println("EFFETTUATI");
        lista_pagamenti_effettuati.stream().forEach(System.out::println);
        System.out.println("RICEVUTI");
        lista_pagamenti_ricevuti.stream().forEach(System.out::println);
    }




}
