package it.unicam.cs.ids2122.cicero.model.services;


import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.TipoFattura;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.beans.Beans;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Accede al DB per inserire e creare una Fattura.
 * Non ici sono metodi per la modifica. Ogni fattura è sempre una nuova fattura.
 * */
public final class ServiceFattura extends AbstractService<BeanFattura> {

    private static ServiceFattura  serviceFattura = null;

    private final String sql_insert = "INSERT INTO public.fatture( id_prenotazione, id_client_origine, id_client_destinatario, " +
            "data_pagamento, importo, valuta, tipo_fattura) VALUES ( ?, ?, ?, ?, ?, ?, ? );";

    private String colonne = "id_fattura, id_prenotazione, id_client_origine, id_client_destinatario, data_pagamento, " +
            "importo, valuta, tipo_fattura";

    private String sql_select_base = "SELECT " + colonne + " FROM public.fatture";


    private ServiceFattura(){

    }

    public static ServiceFattura getInstance(){
        if (serviceFattura==null){
            serviceFattura = new ServiceFattura();
        }return serviceFattura;
    }

    /**
     * "INSERT INTO public.fatture( id_prenotazione, id_client_origine, id_client_destinatario, " +
     *             "data_pagamento, importo, valuta, stato_fattura) VALUES ( ?, ?, ?, ?, ?, ?, ?);";
     * @param fattura
     */
    public void insert(BeanFattura fattura){
        Connection connection = null;
        try{
            connection = DBManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, fattura.getId_prenotazione());
            preparedStatement.setString(2, fattura.getId_client_origine());
            preparedStatement.setString(3, fattura.getId_client_destinatario());
            preparedStatement.setObject(4, fattura.getData_pagamento());
            preparedStatement.setBigDecimal(5, fattura.getImporto());
            preparedStatement.setString(6,fattura.getValuta());
            preparedStatement.setInt(7, fattura.getStatoPagamento().getTipo());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            fattura.setId_fattura(resultSet.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Recupera un insieme di fatture.
     * @param id_fattura
     * @return
     */
    public Set<BeanFattura> sql_select(int id_fattura){
        return parseDataResult( getDataResult(sql_select_base +  " WHERE id_fattura=" +id_fattura+ " ;"));
    }

    /**
     * Recupera un insieme di fatture.
     * @param id_client
     * @return
     */
    public Set<BeanFattura> sql_select(String id_client){
        return parseDataResult( getDataResult(sql_select_base +  " WHERE id_client_origine=" + "'" + id_client + "'" +
                " OR id_client_destinatario=" + "'" + id_client + "';"));
    }

    @Override
    public Set<BeanFattura> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult) {
        Set<BeanFattura> resultSet = new HashSet<>();

        for (Map.Entry<String, HashMap<String, String>> firstEntry: dataResult.entrySet()) {
            BeanFattura beanFattura = new BeanFattura();
            beanFattura.setId_fattura(Integer.parseInt(firstEntry.getKey()));
            for (Map.Entry<String,String> secondEntry: firstEntry.getValue().entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key){
                    case "id_prenotazione": beanFattura.setId_prenotazione(Integer.parseInt(val)); break;
                    case "id_client_origine": beanFattura.setId_client_origine(val);break;
                    case "id_client_destinatario": beanFattura.setId_client_destinatario(val);break;
                    case "data_pagamento": beanFattura.setData_pagamento(LocalDateTime.parse(val.replace(' ', 'T')));break;
                    case "importo": beanFattura.setImporto(new BigDecimal(val)); break;
                    case "valuta": beanFattura.setValuta(val);break;
                    case "tipo_fattura": beanFattura.setStatoPagamento(trasformazione(Integer.parseInt(val)));break;
                    default: break;
                }
                resultSet.add(beanFattura);
            }

        }
        return resultSet;
    }

    private TipoFattura trasformazione(int stato){
        switch (stato){
            case 0: return TipoFattura.PAGAMENTO;
            case 1: return TipoFattura.RIMBORSO;
            case 2: return TipoFattura.LIQUIDAZIONE;
            default:return null;
        }
    }


}
