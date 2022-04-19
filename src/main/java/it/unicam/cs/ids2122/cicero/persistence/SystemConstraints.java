package it.unicam.cs.ids2122.cicero.persistence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface SystemConstraints {

    String DB_PORT = "5432";
    String DB_HOST = "localhost";
    String DB_URI = "jdbc:postgresql";
    String DB_NAME = "cicero";
    String DB_USER = "postgres";
    String DB_PASS = "postgres";

    String ID_SYSTEM = id_client_generator("sistema");

    static String id_client_generator(String mail){
        byte[] bytes = mail.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            bytes = messageDigest.digest(bytes);
            StringBuilder stringBuffer = new StringBuilder();
            for (byte aByte : bytes) {
                stringBuffer.append(Integer.toHexString(0xff & aByte));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
