package org.openjfx.lpi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override public void start(Stage stage) throws IOException {

        org.openjfx.lpi.db.Query.executeSqlFile("SQL/tabelas.sql");

        try {
            Scene scene = new Scene(loadFXML("primary"));
            scene.getStylesheets().add(App.class.getResource("css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.fxml.debug", "true");
        launch();
    }

}