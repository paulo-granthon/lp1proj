package org.openjfx.lpi.controller;

import org.openjfx.lpi.data.Lugar;
import org.openjfx.lpi.data.Pessoa;
import org.openjfx.lpi.data.Veiculo;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Primary {
    
    @FXML private TextField tf_person_name;
    @FXML private TextField tf_person_gender;
    @FXML private DatePicker dp_person_birth;

    @FXML private TableView<Pessoa> table_people;
    @FXML private TableColumn<Pessoa, ?> col_person_name;
    @FXML private TableColumn<Pessoa, ?> col_person_gender;
    @FXML private TableColumn<Pessoa, ?> col_person_birth;

    @FXML private TextField tf_place_country;
    @FXML private TextField tf_place_state;
    @FXML private TextField tf_place_city;

    @FXML private TableView<Lugar> table_place;
    @FXML private TableColumn<Lugar, ?> col_place_country;
    @FXML private TableColumn<Lugar, ?> col_place_state;
    @FXML private TableColumn<Lugar, ?> col_place_city;

    @FXML private TableView<Veiculo> table_vehicle;
    @FXML private TableColumn<Veiculo, ?> col_vehicle_model;
    @FXML private TableColumn<Veiculo, ?> col_vehicle_year;

    @FXML private TextField tf_vehicle_model;
    @FXML private TextField tf_vehicle_year;

    void initialize () {

    }

}
