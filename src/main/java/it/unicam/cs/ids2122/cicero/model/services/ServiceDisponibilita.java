package it.unicam.cs.ids2122.cicero.model.services;


import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ServiceDisponibilita {

    private static ServiceDisponibilita serviceDisponibilita = null;

    ServiceDisponibilita(){}

    public static ServiceDisponibilita getInstance(){
        if(serviceDisponibilita==null){
            serviceDisponibilita = new ServiceDisponibilita();
        }return serviceDisponibilita;
    }

    private String sql_update = "UPDATE public.esperienze SET posti_disponibili= ? WHERE id_esperienza= ? ;";

    private String sql_select = "SELECT posti_disponibili FROM public.esperienze WHERE id_esperienza= ? ";

    /**
     *
     * @param posti da inserire
     * @param id_esperienza id dell' esperienza da modificare
     */
    public void update(int posti, int id_esperienza){
        Connection connection = null;
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = DBManager.getInstance().connect().prepareStatement(sql_update);
            preparedStatement.setInt(1, posti);
            preparedStatement.setInt(2, id_esperienza);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public int select(int id_esperienza){
        Connection connection = null;
        int posti_disponibili = -1;
        try{
            PreparedStatement preparedStatement = DBManager.getInstance().connect().prepareStatement(sql_select);
            preparedStatement.setInt(1, id_esperienza);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            posti_disponibili = resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return posti_disponibili;
    }
}
