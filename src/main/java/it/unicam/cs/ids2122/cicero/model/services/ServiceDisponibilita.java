package it.unicam.cs.ids2122.cicero.model.services;


import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

public final class ServiceDisponibilita  {

    private static ServiceDisponibilita serviceDisponibilita = null;

    ServiceDisponibilita(){}

    public static ServiceDisponibilita getInstance(){
        if(serviceDisponibilita==null){
            serviceDisponibilita = new ServiceDisponibilita();
        }return serviceDisponibilita;
    }

    private String sql_update = "UPDATE public.esperienze SET posti_disponibili=? WHERE id_esperienza=? ;";

    private String sql_select = "SELECT posti_disponibili FROM public.esperienze WHERE id_esperienza=? ";

    /**
     *
     * @param posti da inserire
     * @param id_esperienza id dell' esperienza da modificare
     */
    public void update(int posti, int id_esperienza){
        Connection connection = null;
        try{
            connection = DBManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_update, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, posti);
            preparedStatement.setInt(2, id_esperienza);
            preparedStatement.executeUpdate();
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
            connection = DBManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_select);
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
