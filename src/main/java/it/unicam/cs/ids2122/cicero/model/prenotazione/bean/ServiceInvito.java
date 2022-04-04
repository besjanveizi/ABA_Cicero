package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;

import it.unicam.cs.ids2122.cicero.persistence.PGManager;
import it.unicam.cs.ids2122.cicero.persistence.services.AbstractService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public final class ServiceInvito extends AbstractService<BeanInvito> {

    private final String  sql_insert = "INSERT INTO public.inviti( uid_mittente, id_esperienza, email_destinatario, data_creazione," +
            " data_scadenza_riserva, posti_riservati, importo , valuta) VALUES ( ?, ?, ?, ?, ?, ? , ? , ?);";

    private  final String sql_select = "SELECT * FROM public.inviti";

    private String copy ;


    private static ServiceInvito serviceInvito = null;


    ServiceInvito(){

    }

    public static ServiceInvito getInstance(){
        if(serviceInvito==null){
            serviceInvito = new ServiceInvito();
        }return serviceInvito;
    }

    /**
     * Schema "INSERT INTO public.inviti( uid_mittente, id_esperienza, email_destinatario, data_creazione," +
     *             " data_scadenza_riserva, posti_riservati, importo, valuta) VALUES ( ?, ?, ?, ?, ?, ?, ? ,?);";
     * @param beanInvito
     */
    public void insert(BeanInvito beanInvito){
        Connection connection = null;
        try{
            connection = PGManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, beanInvito.getId_mittente());
            preparedStatement.setInt(2, beanInvito.getId_esperienza());
            preparedStatement.setString(3, beanInvito.getEmail_destinatario());
            preparedStatement.setObject(4, beanInvito.getData_creazione());
            preparedStatement.setObject(5, beanInvito.getData_scadenza_riserva());
            preparedStatement.setInt(6, beanInvito.getPosti_riservati());
            preparedStatement.setBigDecimal(7,beanInvito.getImporto() );
            preparedStatement.setString(8, beanInvito.getValuta());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            beanInvito.setId_invito(resultSet.getInt(1));
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


    public Set<BeanInvito> select(String mail){
            copy =  sql_select + " WHERE email_destinatario='"+ mail +  "';" ;
                return select();
    }

    public Set<BeanInvito> select(){
        Connection connection = null;
        Set<BeanInvito> inviti = new HashSet<>();
        try {
          connection = PGManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(copy);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                BeanInvito beanInvito = new BeanInvito();
                beanInvito.setId_invito(resultSet.getInt("id_invito"));
                beanInvito.setId_mittente(resultSet.getInt("id_mittente"));
                beanInvito.setId_esperienza(resultSet.getInt("id_esperienza"));
                beanInvito.setData_creazione(resultSet.getObject("data_creazione", LocalDateTime.class));
                beanInvito.setData_scadenza_riserva(resultSet.getObject("data_scadenza_riserva", LocalDateTime.class));
                beanInvito.setPosti_riservati(resultSet.getInt("posti_riservati"));
                beanInvito.setImporto(resultSet.getBigDecimal("importo"));
                beanInvito.setValuta(resultSet.getString("valuta"));
                inviti.add(beanInvito);
            }
        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inviti;
    }


    @Override
    public Set<BeanInvito> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult) {
        return null;
    }
}