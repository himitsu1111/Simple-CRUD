package com.haulmont.testtask.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by book on 25.02.2017.
 */
public class ConnectionSingleton {
    private static Connection instance;

    public static synchronized Connection getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.postgresql.Driver");

                instance = DriverManager.getConnection("DATABASE_URL");

//                instance = DriverManager.getConnection("jdbc:postgresql://ec2-54-228-235-185.eu-west-1.compute.amazonaws.com" +
//                        ":5432/decn6648c290uc?user=sfhyuufaikwdbp&password=b777edc6ead336752968de64c492cda7fb0a1a1aac847487c6793024560595e3&" +
//                        "ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
//                "jdbc:postgresql://ec2-107-20-214-225.compute-1.amazonaws.com:5432/databasename?user=someusername&" +
//                        "password=somepassword&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
