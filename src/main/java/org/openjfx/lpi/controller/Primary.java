package org.openjfx.lpi.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.WordUtils;
import org.openjfx.lpi.data.Place;
import org.openjfx.lpi.data.Person;
import org.openjfx.lpi.data.Vehicle;
import org.openjfx.lpi.data.Trip;
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

    // Tab Pessoa
    @FXML private TextField tf_person_name;
    @FXML private TextField tf_person_gender;
    @FXML private DatePicker dp_person_birth;
    @FXML private TableView<Person> table_person;
    @FXML private TableColumn<Person, String> col_person_name;
    @FXML private TableColumn<Person, String> col_person_gender;
    @FXML private TableColumn<Person, Date> col_person_birth;

    // Tab place
    @FXML private TextField tf_place_country;
    @FXML private TextField tf_place_state;
    @FXML private TextField tf_place_city;
    @FXML private TableView<Place> table_place;
    @FXML private TableColumn<Place, String> col_place_country;
    @FXML private TableColumn<Place, String> col_place_state;
    @FXML private TableColumn<Place, String> col_place_city;
    
    // Tab vehicle
    @FXML private TextField tf_vehicle_model;
    @FXML private TextField tf_vehicle_year;
    @FXML private TableView<Vehicle> table_vehicle;
    @FXML private TableColumn<Vehicle, String> col_vehicle_model;
    @FXML private TableColumn<Vehicle, Integer> col_vehicle_year;
    
    // Tab trip
    @FXML private TextField tf_trip_person;
    @FXML private TextField tf_trip_vehicle;
    @FXML private TextField tf_trip_place;
    @FXML private TableView<Trip> table_trip;
    @FXML private TableColumn<Trip, String> col_trip_person;
    @FXML private TableColumn<Trip, String> col_trip_vehicle;
    @FXML private TableColumn<Trip, String> col_trip_place;

    public void initialize () {

        buildTable();

        updateTable();
    }

    void buildTable () {
        col_person_name.setCellValueFactory(new PropertyValueFactory<Person, String>("nome"));
        col_person_gender.setCellValueFactory(new PropertyValueFactory<Person, String>("genero"));
        col_person_birth.setCellValueFactory(new PropertyValueFactory<Person, Date>("nascimentoString"));

        col_place_country.setCellValueFactory(new PropertyValueFactory<Place, String>("pais"));
        col_place_state.setCellValueFactory(new PropertyValueFactory<Place, String>("estado"));
        col_place_city.setCellValueFactory(new PropertyValueFactory<Place, String>("cidade"));
        
        col_vehicle_model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("modelo"));
        col_vehicle_year.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("ano"));

        col_trip_person.setCellValueFactory(new PropertyValueFactory<Trip, String>("modelo"));
        col_trip_vehicle.setCellValueFactory(new PropertyValueFactory<Trip, String>("modelo"));
        col_trip_place.setCellValueFactory(new PropertyValueFactory<Trip, String>("modelo"));
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
            List<Person> pessoas = new ArrayList<>();
            ResultSet queryPessoa = connection.prepareStatement("SELECT * FROM person").executeQuery();
            while (queryPessoa.next()) {
                pessoas.add(new Person(
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
            List<Place> lugares = new ArrayList<>();
            ResultSet queryLugar = connection.prepareStatement("SELECT * FROM place").executeQuery();
            while (queryLugar.next()) {
                lugares.add(new Place(
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
            List<Vehicle> veiculos = new ArrayList<>();
            ResultSet queryVeiculo = connection.prepareStatement("SELECT * FROM vehicle").executeQuery();
            while (queryVeiculo.next()) {
                veiculos.add(new Vehicle(
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
            Query.insertPessoa(new Person(
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
            Query.insertLugar(new Place(
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
            Query.insertVeiculo(new Vehicle(
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
