package org.openjfx.lpi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @FXML private TableView<Pessoa> table_people;
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

    void initialize () {

        buildTable();

        updateTable();
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
        try (Connection conexao = new SQLConnection().connect()) {

            updateTablePeople(conexao);
            updateTablePlace(conexao);
            updateTableVehicle(conexao);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTablePeople (Connection conexao) {
        boolean closeConnection = false;
        try {
            if (conexao == null) conexao = new SQLConnection().connect();
            else closeConnection = true;
            List<Pessoa> pessoas = new ArrayList<>();
            ResultSet queryPessoa = conexao.prepareStatement("SELECT * FROM pessoa").executeQuery();
            while (queryPessoa.next()) {
                pessoas.add(new Pessoa(
                    queryPessoa.getString("nome"),
                    queryPessoa.getString("genero"),
                    queryPessoa.getDate("nascimento")
                ));
            }
            table_people.setItems(FXCollections.observableArrayList((pessoas)));
            table_people.refresh();
            if (closeConnection && conexao != null) conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTablePlace (Connection conexao) {
        boolean closeConnection = false;
        try {
            if (conexao == null) conexao = new SQLConnection().connect();
            else closeConnection = true;
            List<Lugar> lugares = new ArrayList<>();
            ResultSet queryLugar = conexao.prepareStatement("SELECT * FROM lugar").executeQuery();
            while (queryLugar.next()) {
                lugares.add(new Lugar(
                    queryLugar.getString("pais"),
                    queryLugar.getString("estado"),
                    queryLugar.getString("cidade")
                ));
            }
            table_place.setItems(FXCollections.observableArrayList((lugares)));
            table_place.refresh();
            if (closeConnection && conexao != null) conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTableVehicle (Connection conexao) {
        boolean closeConnection = false;
        try {
            if (conexao == null) conexao = new SQLConnection().connect();
            else closeConnection = true;
            List<Veiculo> veiculos = new ArrayList<>();
            ResultSet queryVeiculo = conexao.prepareStatement("SELECT * FROM veiculo").executeQuery();
            while (queryVeiculo.next()) {
                veiculos.add(new Veiculo(
                    queryVeiculo.getString("modelo"),
                    queryVeiculo.getInt("ano")
                ));
            }
            table_vehicle.setItems(FXCollections.observableArrayList((veiculos)));
            table_vehicle.refresh();
            if (closeConnection && conexao != null) conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addPeople (ActionEvent e) {
        Query.insertPessoa(new Pessoa(
            tf_person_name.getText(),
            tf_person_gender.getText(),
            Date.valueOf(dp_person_birth.getValue())
        ));
        updateTablePeople(null);
    }
    
    @FXML
    void addPlace (ActionEvent e) {
        Query.insertLugar(new Lugar(
            tf_place_country.getText(),
            tf_place_state.getText(),
            tf_place_city.getText()
        ));
        updateTablePlace(null);
    }
    
    @FXML
    void addVehicle (ActionEvent e) {
        Query.insertVeiculo(new Veiculo(
            tf_vehicle_model.getText(),
            Integer.parseInt(tf_vehicle_year.getText())
        ));
        updateTableVehicle(null);
    }


}
