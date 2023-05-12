package org.openjfx.lpi.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    public static Connection connect() throws IOException {

        String[] env = new String[] {
            "host",
            "port",
            "userName",
            "password",
            "database",
        };

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(".env"));

            for (int i = 0; i < env.length; i++) {
                env[i] = br.readLine().split(":")[1];                
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (br != null) br.close();
        }

        String host = env[0];
        String port = env[1];
        String userName = env[2];
        String password = env[3];
        String database = env[4];

        String driver = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        Connection connection = null;

        try {

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(driver, userName, password);
            connection.setAutoCommit(false);

        } catch (ClassNotFoundException ex) {
            System.out.println("SQLConnector.connect() -- Erro: Driver do banco de dados não localizado!");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("SQLConnector.connect() -- Erro ao conectar com o banco de dados!");
            ex.printStackTrace();
        } 
        return connection;

    }

}