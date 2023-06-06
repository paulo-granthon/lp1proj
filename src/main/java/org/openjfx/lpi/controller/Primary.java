package org.openjfx.lpi.controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;
import org.openjfx.lpi.controller.utils.HasName;
import org.openjfx.lpi.controller.utils.LookupTextField;
import org.openjfx.lpi.controller.utils.TripWrapper;
import org.openjfx.lpi.db.Query;
import org.openjfx.lpi.model.Person;
import org.openjfx.lpi.model.Place;
import org.openjfx.lpi.model.Trip;
import org.openjfx.lpi.model.Vehicle;
import org.openjfx.lpi.model.relation.Parade;
import org.openjfx.lpi.model.relation.Traveler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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
    private LookupTextField<Person> lutf_trip_person;
    private LookupTextField<Vehicle> lutf_trip_vehicle;
    private LookupTextField<Place> lutf_trip_place;

    private ObservableList<Person> selectedPeople = FXCollections.observableList(new LinkedList<Person>());
    private ObservableList<Place> selectedPlaces = FXCollections.observableList(new LinkedList<Place>());

    @FXML private TextField tf_trip_person;
    @FXML private TextField tf_trip_vehicle;
    @FXML private TextField tf_trip_place;

    @FXML private FlowPane fp_trip_people;
    @FXML private FlowPane fp_trip_places;

    @FXML private TableView<TripWrapper> table_trip;
    @FXML private TableColumn<TripWrapper, String> col_trip_people;
    @FXML private TableColumn<TripWrapper, String> col_trip_vehicle;
    @FXML private TableColumn<TripWrapper, String> col_trip_place;

    // Loaded data
    private Person[] loadedPeople;
    private Place[] loadedPlaces;
    private Vehicle[] loadedVehicles;
    private TripWrapper[] loadedTrips;

    public void initialize () {
        buildTable();
        updateTable();
    }

    void buildTable () {
        tf_trip_person = (lutf_trip_person = LookupTextField.<Person, HBox>overrideTextField(tf_trip_person, new Person[0]));
        tf_trip_place = (lutf_trip_place = LookupTextField.<Place, HBox>overrideTextField(tf_trip_place, new Place[0]));
        tf_trip_vehicle = (lutf_trip_vehicle = LookupTextField.<Vehicle, HBox>overrideTextField(tf_trip_vehicle, new Vehicle[0]));

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
            Optional<Connection> connectionOptional = Query.connect();
            System.out.println(connectionOptional == null ? "Sem conexão" : "Conectado");

            updateTablePerson(connectionOptional);
            updateTablePlace(connectionOptional);
            updateTableVehicle(connectionOptional);

            updateTableTrip(connectionOptional);

            Query.close(connectionOptional);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTablePerson (Optional<Connection> connectionOptional) {
        loadedPeople = Query.selectAllPerson(connectionOptional);
        table_person.setItems(FXCollections.observableArrayList(loadedPeople));
        table_person.refresh();

        lutf_trip_person.updateSuggestions(loadedPeople);
        lutf_trip_person.clear();
    }
    private void updateTablePlace (Optional<Connection> connectionOptional) {
        loadedPlaces = Query.selectAllPlace(connectionOptional);
        table_place.setItems(FXCollections.observableArrayList(loadedPlaces));
        table_place.refresh();

        lutf_trip_place.updateSuggestions(loadedPlaces);
        lutf_trip_place.clear();
    }
    private void updateTableVehicle (Optional<Connection> connectionOptional) {
        loadedVehicles = Query.selectAllVehicle(connectionOptional);
        table_vehicle.setItems(FXCollections.observableArrayList(loadedVehicles));
        table_vehicle.refresh();

        lutf_trip_vehicle.updateSuggestions(loadedVehicles);
        lutf_trip_vehicle.clear();
    }
    private void updateTableTrip (Optional<Connection> connectionOptional) {
        loadedTrips = Query.selectAllTrip(
            Arrays.asList(loadedPeople),
            Arrays.asList(loadedPlaces),
            loadedVehicles,
            connectionOptional
        );
        table_trip.setItems(FXCollections.observableArrayList(Arrays.asList(loadedTrips)));
        table_trip.refresh();
    }

    @FXML void addPerson (ActionEvent e) {
        try {
            Optional<Connection> connectionOptional = Query.connect();
            Query.insertPerson(
                new Person(
                    WordUtils.capitalizeFully(tf_person_name.getText()),
                    WordUtils.capitalizeFully(tf_person_gender.getText()),
                    Date.valueOf(dp_person_birth.getValue())
                ),
                connectionOptional
            );
            tf_person_name.clear();
            tf_person_gender.clear();
            dp_person_birth.setValue(null);
            updateTablePerson(connectionOptional);
            Query.close(connectionOptional);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML void addPlace (ActionEvent e) {
        try {
            Optional<Connection> connectionOptional = Query.connect();
            Query.insertPlace(
                new Place(
                    WordUtils.capitalizeFully(tf_place_country.getText()),
                    WordUtils.capitalizeFully(tf_place_state.getText()),
                    WordUtils.capitalizeFully(tf_place_city.getText())
                ),
                connectionOptional
            );
            tf_place_country.clear();
            tf_place_state.clear();
            tf_place_city.clear();
            updateTablePlace(connectionOptional);
            Query.close(connectionOptional);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML void addVehicle (ActionEvent e) {
        try {
            Optional<Connection> connectionOptional = Query.connect();
            Query.insertVehicle(
                new Vehicle(
                    WordUtils.capitalizeFully(tf_vehicle_model.getText()),
                    Integer.parseInt(tf_vehicle_year.getText())
                ),
                connectionOptional
            );
            tf_vehicle_model.clear();
            tf_vehicle_year.clear();
            updateTableVehicle(connectionOptional);
            Query.close(connectionOptional);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML void addTrip (ActionEvent e) {
        Vehicle vehicle = lutf_trip_vehicle.getSelectedItem();
        System.out.println("oi? " + selectedPeople.isEmpty() + " || " + selectedPlaces.isEmpty() + " || " + vehicle == null);
        if (selectedPeople.isEmpty() || selectedPlaces.isEmpty() || vehicle == null) return;
        try {
            Optional<Connection> connectionOptional = Query.connect();
            int trip_id = Query.insertTrip(
                new Trip(vehicle.getId()),
                connectionOptional
            ).orElseThrow(() -> new Exception("Error inserting Trip into database"));
            for (int person : selectedPeople.stream().map((Person person) -> person.getId()).collect(Collectors.toList())) {
                Query.insertTraveler(new Traveler(trip_id, person), connectionOptional);
            }
            for (int place : selectedPlaces.stream().map((Place place) -> place.getId()).collect(Collectors.toList())) {
                Query.insertParade(new Parade(trip_id, place), connectionOptional);
            }
            fp_trip_people.getChildren().clear();
            fp_trip_places.getChildren().clear();
            lutf_trip_person.clear();
            lutf_trip_vehicle.clear();
            lutf_trip_vehicle.clear();
            updateTableTrip(connectionOptional);
            Query.close(connectionOptional);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private <T extends HasName> void addTripGeneric (
        LookupTextField<T> lookupTextField,
        FlowPane flowPane,
        ObservableList<T> observableList
    ) {

        // get the selectedPerson of the LoopkupTextField
        T selectedItem = lookupTextField.getSelectedItem();

        // if null, cancel
        if (selectedItem == null) return;

        // Add the user to the observableList List so that when the ResultCenter is inserted,
        // all the selected users can be assigned the member_cr relation to the ResultCenter inserted
        observableList.add(selectedItem);

        // Remove the suggestion from LookupTextField and clear the contents of lookupTextField
        lookupTextField.removeSuggestion(selectedItem);
        lookupTextField.clear();

        // Create a label with the Person's name
        Label label = new Label(selectedItem.getName());
        HBox.setMargin(label, new Insets(0, 0, 0, 0));
        label.setPadding(new Insets(0));
        label.setFont(Font.font("Arial", 12));

        // Create a button to remove the user from the FlowPane
        Button removeButton = new Button("X");
        HBox.setMargin(removeButton, new Insets(0, 0, 0, 4));
        removeButton.setPadding(new Insets(0, 4, 0, 4));
        removeButton.setMinHeight(18);
        removeButton.setMaxHeight(18);
        removeButton.setPrefHeight(18);
        removeButton.setAlignment(Pos.CENTER);
        removeButton.getStyleClass().setAll("remove-btn");

        // Create an HBox to wrap both 
        HBox itemContainer = new HBox(label, removeButton);
        itemContainer.setPadding(new Insets(2));
        itemContainer.setAlignment(Pos.CENTER_LEFT);

        // Add the container to the FlowPane
        flowPane.getChildren().add(itemContainer);

        // Add OnAction to the button to remove the user from observableList, 
        removeButton.setOnAction(e -> {
            flowPane.getChildren().remove(itemContainer);
            lookupTextField.addSuggestion(selectedItem);
            observableList.remove(selectedItem);
        });

    }

    @FXML void addTripPerson(ActionEvent event) {
        this.<Person>addTripGeneric(lutf_trip_person, fp_trip_people, selectedPeople);
    }

    @FXML void addTripPlace(ActionEvent event) {
        this.<Place>addTripGeneric(lutf_trip_place, fp_trip_places, selectedPlaces);
    }

}
