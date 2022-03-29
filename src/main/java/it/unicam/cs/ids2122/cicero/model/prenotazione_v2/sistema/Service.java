package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.sistema;

import java.sql.SQLException;

public interface Service<T>  {

   T menu() throws SQLException;

}
