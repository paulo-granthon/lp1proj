package org.openjfx.lpi.db;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    public static Connection connect() {
        String[] env = new String[] {
            "host",
            "port",
            "database",
            "userName",
            "password",
        };

        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
            for (int i = 0; i < env.length; i++) env[i] = br.readLine().split(":")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("SQLConnector.connect() -- Erro: Driver do banco de dados não localizado!");
            ex.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://" + env[0] + ":" + env[1] + "/" + env[2], env[3], env[4]
            );
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException ex) {
            System.out.println("SQLConnector.connect() -- Erro ao conectar com o banco de dados!");
            ex.printStackTrace();
        }
        return null;
    }

}