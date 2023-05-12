package org.openjfx.lpi.controller;

// import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
// import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.WordUtils;
import org.openjfx.lpi.data.Lugar;
import org.openjfx.lpi.data.Pessoa;
import org.openjfx.lpi.data.Veiculo;
import org.openjfx.lpi.db.Query;
import org.openjfx.lpi.db.SQLConnection;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Primary {
    
    @FXML private TextField tf_person_name;
    @FXML private TextField tf_person_gender;
    @FXML private DatePicker dp_person_birth;

    @FXML private TableView<Pessoa> table_person;
    @FXML private TableColumn<Pessoa, String> col_person_name;
    @FXML private TableColumn<Pessoa, String> col_person_gender;
    @FXML private TableColumn<Pessoa, Date> col_person_birth;

    @FXML private TextField tf_place_country;
    @FXML private TextField tf_place_state;
    @FXML private TextField tf_place_city;

    @FXML private TableView<Lugar> table_place;
    @FXML private TableColumn<Lugar, String> col_place_country;
    @FXML private TableColumn<Lugar, String> col_place_state;
    @FXML private TableColumn<Lugar, String> col_place_city;

    @FXML private TableView<Veiculo> table_vehicle;
    @FXML private TableColumn<Veiculo, String> col_vehicle_model;
    @FXML private TableColumn<Veiculo, Integer> col_vehicle_year;

    @FXML private TextField tf_vehicle_model;
    @FXML private TextField tf_vehicle_year;

    public void initialize () {

        buildTable();

        updateTable();
    }

    void buildTable () {
        col_person_name.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
        col_person_gender.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("genero"));
        col_person_birth.setCellValueFactory(new PropertyValueFactory<Pessoa, Date>("nascimentoString"));

        col_place_country.setCellValueFactory(new PropertyValueFactory<Lugar, String>("pais"));
        col_place_state.setCellValueFactory(new PropertyValueFactory<Lugar, String>("estado"));
        col_place_city.setCellValueFactory(new PropertyValueFactory<Lugar, String>("cidade"));
        
        col_vehicle_model.setCellValueFactory(new PropertyValueFactory<Veiculo, String>("modelo"));
        col_vehicle_year.setCellValueFactory(new PropertyValueFactory<Veiculo, Integer>("ano"));
    }

    private void updateTable () {
        // try {
            // Connection connection = SQLConnection.connect();
            // System.out.println(connection == null ? "Sem conexão" : "Conectado");

            updateTablePerson(null);
            updateTablePlace(null);
            updateTableVehicle(null);

            // connection.commit();
            // connection.close();

        // } catch (IOException | SQLException e) {
        //     e.printStackTrace();
        // }
    }

    private void updateTablePerson (Connection connection) {
        boolean commitAndClose = false;
        try {
            if (connection == null) connection = SQLConnection.connect();
            else commitAndClose = true;
            List<Pessoa> pessoas = new ArrayList<>();
            ResultSet queryPessoa = connection.prepareStatement("SELECT * FROM person").executeQuery();
            while (queryPessoa.next()) {
                pessoas.add(new Pessoa(
                    queryPessoa.getString("prsn_name"),
                    queryPessoa.getString("prsn_gender"),
                    queryPessoa.getDate("prsn_birth")
                ));
            }
            table_person.setItems(FXCollections.observableArrayList((pessoas)));
            table_person.refresh();
            if (commitAndClose && connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTablePlace (Connection connection) {
        boolean commitAndClose = false;
        try {
            if (connection == null) connection = SQLConnection.connect();
            else commitAndClose = true;
            List<Lugar> lugares = new ArrayList<>();
            ResultSet queryLugar = connection.prepareStatement("SELECT * FROM place").executeQuery();
            while (queryLugar.next()) {
                lugares.add(new Lugar(
                    queryLugar.getString("plce_country"),
                    queryLugar.getString("plce_state"),
                    queryLugar.getString("plce_city")
                ));
            }
            table_place.setItems(FXCollections.observableArrayList((lugares)));
            table_place.refresh();
            if (commitAndClose && connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTableVehicle (Connection connection) {
        boolean commitAndClose = false;
        try {
            if (connection == null) connection = SQLConnection.connect();
            else commitAndClose = true;
            List<Veiculo> veiculos = new ArrayList<>();
            ResultSet queryVeiculo = connection.prepareStatement("SELECT * FROM vehicle").executeQuery();
            while (queryVeiculo.next()) {
                veiculos.add(new Veiculo(
                    queryVeiculo.getString("vhcl_model"),
                    queryVeiculo.getInt("vhcl_year")
                ));
            }
            table_vehicle.setItems(FXCollections.observableArrayList((veiculos)));
            table_vehicle.refresh();
            if (commitAndClose && connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addPerson (ActionEvent e) {
        try {
            Query.insertPessoa(new Pessoa(
                WordUtils.capitalizeFully(tf_person_name.getText()),
                WordUtils.capitalizeFully(tf_person_gender.getText()),
                Date.valueOf(dp_person_birth.getValue())
            ));
            tf_person_name.clear();
            tf_person_gender.clear();
            dp_person_birth.setValue(null);
            updateTablePerson(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void addPlace (ActionEvent e) {
        try {
            Query.insertLugar(new Lugar(
                WordUtils.capitalizeFully(tf_place_country.getText()),
                WordUtils.capitalizeFully(tf_place_state.getText()),
                WordUtils.capitalizeFully(tf_place_city.getText())
            ));
            tf_place_country.clear();
            tf_place_state.clear();
            tf_place_city.clear();
            updateTablePlace(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void addVehicle (ActionEvent e) {
        try {
            Query.insertVeiculo(new Veiculo(
                WordUtils.capitalizeFully(tf_vehicle_model.getText()),
                Integer.parseInt(tf_vehicle_year.getText())
            ));
            tf_vehicle_model.clear();
            tf_vehicle_year.clear();
            updateTableVehicle(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
