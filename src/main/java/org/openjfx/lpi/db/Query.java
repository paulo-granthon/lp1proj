package org.openjfx.lpi.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.openjfx.lpi.data.Place;
import org.openjfx.lpi.data.Person;
import org.openjfx.lpi.data.Vehicle;
import org.openjfx.lpi.data.Record;

public class Query {

    public static <T extends Record> void insert (Table table, Class<T> type, T record) {
        try {
            Connection conexao = SQLConnection.connect();
            PreparedStatement statement = conexao.prepareStatement(table.getInsertQuery());
            Record.apply(statement, record);
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertPerson (Person p) {
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

    public static void insertPlace (Place p) {
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

    public static void insertVehicle (Vehicle p) {
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

    public static void insertTrip (int[] people, int vhcl_id, int[] places) {
        try {
            Connection conexao = SQLConnection.connect();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO trip (vhcl_id) values (?) RETURNING id");
            statement.setInt(1, vhcl_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int trip_id = resultSet.getInt(1);
            for (int i = 0; i < people.length; i++) {
                statement = conexao.prepareStatement("INSERT INTO traveler (trip_id, prsn_id) values (?, ?)");
                statement.setInt(trip_id, 1);
                statement.setInt(people[i], 2);
                statement.execute();
            }
            for (int i = 0; i < places.length; i++) {
                statement = conexao.prepareStatement("INSERT INTO parade (trip_id, plce_id) values (?, ?)");
                statement.setInt(trip_id, 1);
                statement.setInt(places[i], 2);
                statement.execute();
            }
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
