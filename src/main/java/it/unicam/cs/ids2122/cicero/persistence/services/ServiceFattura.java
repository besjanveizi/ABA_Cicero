package it.unicam.cs.ids2122.cicero.persistence.services;


import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPagamento;
import it.unicam.cs.ids2122.cicero.persistence.PGManager;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;


public final class ServiceFattura extends AbstractService<BeanFattura> {

    private static ServiceFattura  serviceFattura = null;

    private final String sql_insert = "INSERT INTO public.fatture( id_prenotazione, id_client_origine, id_client_destinatario, " +
            "data_pagamento, importo, valuta, stato_fattura, posti_pagati) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";

    private String sql_select = "SELECT * FROM public.fatture ";

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

    public Set<BeanFattura> select()  {
        Connection connection = null;
        Set<BeanFattura> fatture = new HashSet<>();
        try{
            PreparedStatement preparedStatement = PGManager.getInstance().connect().prepareStatement(this.sql_select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                BeanFattura beanFattura = new BeanFattura();
                beanFattura.setId_fattura(resultSet.getInt("id_fattura"));
                beanFattura.setId_prenotazione(resultSet.findColumn("id_prenotazione"));
                beanFattura.setId_client_destinatario(resultSet.getString("id_client_destinatario"));
                beanFattura.setId_client_origine(resultSet.getString("id_client_origine"));
                beanFattura.setData_pagamento(resultSet.getObject("data_pagamento", LocalDateTime.class));
                beanFattura.setImporto(resultSet.getBigDecimal("importo"));
                beanFattura.setValuta(resultSet.getString("valuta"));
                beanFattura.setPosti_pagati(resultSet.getInt("posti_pagati"));
                beanFattura.setStatoPagamento(trasformazione(resultSet.getInt("stato_fattura")));
                fatture.add(beanFattura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return fatture;
    }

    public Set<BeanFattura> select(String id_Client){
        this.sql_select = sql_select+ " WHERE id_client_origine=" +"'"+id_Client+"'"+
                " OR id_client_destinatario=" +"'"+id_Client+"';" ;
        return select();
    }

    private StatoPagamento trasformazione(int stato){
        switch (stato){
            case 0: return StatoPagamento.ADMIN_ADMIN;
            case 1: return StatoPagamento.ADMIN_TURISTA;
            case 2: return StatoPagamento.ADMIN_CICERONE;
            default:return null;
        }
    }

    @Override
    public Set<BeanFattura> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult) {
        return null;
    }
}
