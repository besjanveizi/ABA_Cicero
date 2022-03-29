package it.unicam.cs.ids2122.cicero.model.prenotazione_v2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface SystemConstraints {

    /**
     * termine ultimo, in giorni, per la richiesta di rimborso dalla
     * conclusione di un esperienza
     */
    int TERMINE_ULTIMO = 2;

    /**
     * identificativo per transazioni economiche
     * generato da test test
     */
    String ID_CLIENT = id_client_generator("test@test.it");


    String DBUSER = "cicero";
    String DBPASS = "asdasd12345";


    static String id_client_generator(String mail){
        byte[] bytes = mail.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            bytes = messageDigest.digest(bytes);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length ; i++) {
                stringBuffer.append(Integer.toHexString(0xff & bytes[i]));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }




}
