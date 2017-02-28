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
                Class.forName("org.hsqldb.jdbcDriver");
                instance = DriverManager.getConnection("jdbc:hsqldb:file:C:\\db\\hsqldb\\demodb", "sa", "");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
