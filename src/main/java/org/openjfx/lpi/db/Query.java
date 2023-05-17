package org.openjfx.lpi.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.openjfx.lpi.data.Place;
import org.openjfx.lpi.data.Person;
import org.openjfx.lpi.data.Vehicle;

public class Query {

    public static <T extends Record> void insert (Table table, Class<T> type, T record) {
        try {
            Connection conexao = SQLConnection.connect();
            PreparedStatement statement = conexao.prepareStatement(table.getInsertQuery());
            T.apply(statement, record);
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertPessoa (Person p) {
        try {
            Connection conexao = SQLConnection.connect();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO person (prsn_name, prsn_gender, prsn_birth) values (?, ?, ?)");
            statement.setString(1, p.getName());
            statement.setString(2, p.getGender());
            statement.setDate(3, p.getBirth());
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertLugar (Place p) {
        try {
            Connection conexao = SQLConnection.connect();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO place (plce_country, plce_state, plce_city) values (?, ?, ?)");
            statement.setString(1, p.getCountry());
            statement.setString(2, p.getState());
            statement.setString(3, p.getCity());
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertVeiculo (Vehicle p) {
        try {
            Connection conexao = SQLConnection.connect();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO vehicle (vhcl_model, vhcl_year) values (?, ?)");
            statement.setString(1, p.getModel());
            statement.setInt(2, p.getYear());
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeSqlFile (String file_path) {
        try {
            Connection conexao = SQLConnection.connect();
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            StringBuilder sb = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append(System.lineSeparator());
            }
            conexao.createStatement().execute(sb.toString());
            br.close();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
