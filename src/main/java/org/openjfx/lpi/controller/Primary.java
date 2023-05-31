package org.openjfx.lpi.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.WordUtils;
import org.openjfx.lpi.data.Place;
import org.openjfx.lpi.controller.utils.TripWrapper;
import org.openjfx.lpi.data.Person;
import org.openjfx.lpi.data.Vehicle;
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
import javafx.scene.layout.FlowPane;

public class Primary {

    // Tab Person
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

    @FXML private FlowPane lv_trip_people;
    @FXML private FlowPane lv_trip_places;

    @FXML private TableView<TripWrapper> table_trip;
    @FXML private TableColumn<TripWrapper, String> col_trip_people;
    @FXML private TableColumn<TripWrapper, String> col_trip_vehicle;
    @FXML private TableColumn<TripWrapper, String> col_trip_place;


    public void initialize () {

        buildTable();

        updateTable();
    }

    void buildTable () {
        col_person_name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        col_person_gender.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        col_person_birth.setCellValueFactory(new PropertyValueFactory<Person, Date>("birthString"));
        col_person_name.setReorderable(false);
        col_person_gender.setReorderable(false);
        col_person_birth.setReorderable(false);

        col_place_country.setCellValueFactory(new PropertyValueFactory<Place, String>("country"));
        col_place_state.setCellValueFactory(new PropertyValueFactory<Place, String>("state"));
        col_place_city.setCellValueFactory(new PropertyValueFactory<Place, String>("city"));
        col_place_country.setReorderable(false);
        col_place_state.setReorderable(false);
        col_place_city.setReorderable(false);

        col_vehicle_model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
        col_vehicle_year.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("year"));
        col_vehicle_model.setReorderable(false);
        col_vehicle_year.setReorderable(false);

        col_trip_people.setCellValueFactory(new PropertyValueFactory<TripWrapper, String>("people"));
        col_trip_vehicle.setCellValueFactory(new PropertyValueFactory<TripWrapper, String>("vehicle"));
        col_trip_place.setCellValueFactory(new PropertyValueFactory<TripWrapper, String>("place"));
        col_trip_people.setReorderable(false);
        col_trip_vehicle.setReorderable(false);
        col_trip_place.setReorderable(false);

    }

    private void updateTable () {
        try {
            Connection connection = SQLConnection.connect();
            System.out.println(connection == null ? "Sem conexão" : "Conectado");

            updateTablePerson(connection);
            updateTablePlace(connection);
            updateTableVehicle(connection);

            

            connection.commit();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTablePerson (Connection connection) {
        try {
            boolean commitAndClose = connection == null ? (connection = SQLConnection.connect()) != null : false;
            ResultSet queryPerson = connection.prepareStatement("SELECT * FROM person").executeQuery();
            List<Person> Persons = new ArrayList<>();
            while (queryPerson.next()) {
                Persons.add(new Person(
                    queryPerson.getString("prsn_name"),
                    queryPerson.getString("prsn_gender"),
                    queryPerson.getDate("prsn_birth")
                ));
            }
            if (commitAndClose && connection != null) {
                connection.commit();
                connection.close();
                System.out.println("oi close Person");
            }
            table_person.setItems(FXCollections.observableArrayList((Persons)));
            table_person.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTablePlace (Connection connection) {
        try {
            boolean commitAndClose = connection == null ? (connection = SQLConnection.connect()) != null : false;
            ResultSet queryPlace = connection.prepareStatement("SELECT * FROM place").executeQuery();
            List<Place> Placees = new ArrayList<>();
            while (queryPlace.next()) {
                Placees.add(new Place(
                    queryPlace.getString("plce_country"),
                    queryPlace.getString("plce_state"),
                    queryPlace.getString("plce_city")
                ));
            }
            if (commitAndClose && connection != null) {
                connection.commit();
                connection.close();
                System.out.println("oi close place");
            }
            table_place.setItems(FXCollections.observableArrayList((Placees)));
            table_place.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTableVehicle (Connection connection) {
        try {
            boolean commitAndClose = connection == null ? (connection = SQLConnection.connect()) != null : false;
            ResultSet queryVehicle = connection.prepareStatement("SELECT * FROM vehicle").executeQuery();
            List<Vehicle> Vehicles = new ArrayList<>();
            while (queryVehicle.next()) {
                Vehicles.add(new Vehicle(
                    queryVehicle.getString("vhcl_model"),
                    queryVehicle.getInt("vhcl_year")
                ));
            }
            if (commitAndClose && connection != null) {
                connection.commit();
                connection.close();
                System.out.println("oi close Vehicle");
            }
            table_vehicle.setItems(FXCollections.observableArrayList((Vehicles)));
            table_vehicle.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addPerson (ActionEvent e) {
        try {
            Query.insertPerson(new Person(
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
            Query.insertPlace(new Place(
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
            Query.insertVehicle(new Vehicle(
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
