package com.backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect{

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("oracle.jdbc.OracleDriver");

            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:ORCL",
                    "System",
                    "Tejas001"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}