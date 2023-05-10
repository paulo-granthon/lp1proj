package org.openjfx.lpi.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.lpi.data.Lugar;
import org.openjfx.lpi.data.Pessoa;
import org.openjfx.lpi.data.Veiculo;

import javafx.collections.FXCollections;
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

    @FXML private TableView<Pessoa> table_people;
    @FXML private TableColumn<Pessoa, String> col_person_name;
    @FXML private TableColumn<Pessoa, String> col_person_gender;
    @FXML private TableColumn<Pessoa, LocalDate> col_person_birth;

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

    void initialize () {

        buildTable();
    }

    void buildTable () {
        col_person_name.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_person_gender.setCellValueFactory(new PropertyValueFactory<>("genero"));
        col_person_birth.setCellValueFactory(new PropertyValueFactory<>("nascimentoString"));
        col_place_country.setCellValueFactory(new PropertyValueFactory<>("pais"));
        col_place_state.setCellValueFactory(new PropertyValueFactory<>("estado"));
        col_place_city.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        col_vehicle_model.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        col_vehicle_year.setCellValueFactory(new PropertyValueFactory<>("ano"));
    }

    private void updateTable () {
    
        List<Pessoa> pessoas = new ArrayList<>();
        List<Lugar> lugares = new ArrayList<>();
        List<Veiculo> veiculos = new ArrayList<>();

        table_people.setItems(FXCollections.observableArrayList((pessoas)));
        table_people.refresh();
        table_place.setItems(FXCollections.observableArrayList((lugares)));
        table_place.refresh();
        table_vehicle.setItems(FXCollections.observableArrayList((veiculos)));
        table_vehicle.refresh();
    }


}
