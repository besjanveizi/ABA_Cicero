package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Accede al DB per eliminare, creare e recuperare uno o più inviti.
 */
public final class ServiceInvito extends AbstractService<BeanInvito> {

    private final String  sql_insert = "INSERT INTO public.inviti( uid_mittente, id_esperienza, email_destinatario, data_creazione," +
            " data_scadenza_riserva, posti_riservati, costo_totale , valuta) VALUES ( ?, ?, ?, ?, ?, ? , ? , ?);";

    private final String colonne =
            "id_invito, uid_mittente, id_esperienza, email_destinatario, data_creazione, data_scadenza_riserva, posti_riservati, costo_totale, valuta";

    private final String sql_select_base = "SELECT " + colonne + " FROM public.inviti ";

    private static ServiceInvito serviceInvito = null;

    ServiceInvito(){ }

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
            connection = DBManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, beanInvito.getId_mittente());
            preparedStatement.setInt(2, beanInvito.getId_esperienza());
            preparedStatement.setString(3, beanInvito.getEmail_destinatario());
            preparedStatement.setObject(4, beanInvito.getData_creazione());
            preparedStatement.setObject(5, beanInvito.getData_scadenza_riserva());
            preparedStatement.setInt(6, beanInvito.getPosti_riservati());
            preparedStatement.setBigDecimal(7,beanInvito.getImporto() );
            preparedStatement.setString(8, beanInvito.getValuta());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
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


    /**
     * Elimina l' invito dal DB
     * @param id_invito da eliminare
     */
    public void delete(int id_invito) {
        String query = "delete from public.inviti where id_invito="+id_invito+";";
        Connection connection = null;
        try{
            connection = DBManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
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
     * Recupera un insieme di inviti, rispettivamente dell'utente autenticato.
     * @param email
     * @return
     */
    public Set<BeanInvito> select(String email){
        return parseDataResult( getDataResult(sql_select_base + " WHERE email_destinatario=" + "'"+email+"'"+";" ));
    }


    public Set<BeanInvito> download(Esperienza e) {
        return parseDataResult(getDataResult(sql_select_base + " WHERE id_esperienza=" + e.getId()));
    }

    @Override
    public Set<BeanInvito> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult) {
        Set<BeanInvito> resultSet = new HashSet<>();
        for (Map.Entry<String, HashMap<String, String>> firstEntry : dataResult.entrySet()) {

            BeanInvito beanInvito = new BeanInvito();
            beanInvito.setId_invito(Integer.parseInt(firstEntry.getKey()));

            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "uid_mittente":
                        beanInvito.setId_mittente(Integer.parseInt(val));break;
                    case "id_esperienza":
                        beanInvito.setId_esperienza(Integer.parseInt(val));break;
                    case "email_destinatario":
                        beanInvito.setEmail_destinatario(val);break;
                    case "data_creazione":
                        beanInvito.setData_creazione(LocalDateTime.parse(val.replace(' ', 'T')));break;
                    case "data_scadenza_riserva":
                        beanInvito.setData_scadenza_riserva(LocalDateTime.parse(val.replace(' ', 'T')));break;
                    case "posti_riservati":
                        beanInvito.setPosti_riservati(Integer.parseInt(val));break;
                    case "costo_totale":
                        beanInvito.setImporto(new BigDecimal(val));break;
                    case "valuta":
                        beanInvito.setValuta(val);break;
                    default:
                        break;
                }
            }
            resultSet.add(beanInvito);
        }
        return resultSet;
    }
}