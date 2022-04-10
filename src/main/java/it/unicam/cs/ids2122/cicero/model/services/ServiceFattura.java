package it.unicam.cs.ids2122.cicero.model.services;


import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.TipoFattura;

import java.sql.*;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;


public final class ServiceFattura extends AbstractService<BeanFattura> {

    private static ServiceFattura  serviceFattura = null;

    private final String sql_insert = "INSERT INTO public.fatture( id_prenotazione, id_client_origine, id_client_destinatario, " +
            "data_pagamento, importo, valuta, tipo_fattura, posti_pagati) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";


    private ServiceFattura(){

    }

    public static ServiceFattura getInstance(){
        if (serviceFattura==null){
            serviceFattura = new ServiceFattura();
        }return serviceFattura;
    }

    /**
     * "INSERT INTO public.fatture( id_prenotazione, id_client_origine, id_client_destinatario, " +
     *             "data_pagamento, importo, valuta, stato_fattura, posti_pagati) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
     * @param fattura
     */
    public void insert(BeanFattura fattura){
        Connection connection = null;
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, fattura.getId_prenotazione());
            preparedStatement.setString(2, fattura.getId_client_origine());
            preparedStatement.setString(3, fattura.getId_client_destinatario());
            preparedStatement.setObject(4, fattura.getData_pagamento());
            preparedStatement.setBigDecimal(5, fattura.getImporto());
            preparedStatement.setString(6,fattura.getValuta());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fattura.setId_fattura(resultSet.getInt(1));
            connection.commit();
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



    private String colonne = "id_fattura, id_prenotazione, id_client_origine, id_client_destinatario, data_pagamento, " +
            "importo, valuta, tipo_fattura, posti_pagati";

    private String sql_select_base = "SELECT " + colonne + " FROM public.fatture";


    public Set<BeanFattura> sql_select(String id_client){
        return parseDataResult( getDataResult(sql_select_base +  " WHERE id_client_origine=" + "'" + id_client + "'" +
                " OR id_client_destinatario=" + "'" + id_client + "';"));
    }

    @Override
    public Set<BeanFattura> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult) {
        return null;
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
