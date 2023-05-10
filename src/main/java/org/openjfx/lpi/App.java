package org.openjfx.lpi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.openjfx.lpi.authentication.Authentication;
import org.openjfx.lpi.authentication.Profile;
import org.openjfx.lpi.view_controllers.BaseController;
import org.openjfx.lpi.view_controllers.templates.ViewButtonController;
import org.openjfx.lpi.views_manager.View;
import org.openjfx.lpi.views_manager.ViewsManager;

// import org.openjfx.lpi.authentication.User;
import org.openjfx.lpi.database.QueryLibs;

public class App extends Application {
    
    // mude o perfil de acesso para logar com diferentes permissões
    private static final Profile access = Profile.Colaborador;
    
    private static Scene scene;
    private static Stage stage;
    private static void setStage (Stage newStage) { stage = newStage; }
    
    private static String currentViewFxmlFile;
    
    private static BaseController baseController;
    
    @Override
    public void start(Stage stage) throws IOException {

        // QueryLibs.executeSqlFile("./SQL/tabelas.sql");
        // QueryLibs.executeSqlFile("./SQL/views.sql");

        // QueryLibs.insertUser(new User("Fulano colaborador 0", Profile.Colaborador, "e@xem.plo", "0", "0"));
        // QueryLibs.insertUser(new User("Fulano gestor 0", Profile.Gestor, "e@xem.plo", "0", "0"));
        // QueryLibs.insertUser(new User("Cicrano col 0", Profile.Colaborador, "e@xem.plo", "0", "0"));
        // QueryLibs.insertUser(new User("Cicrano ges 0", Profile.Gestor, "e@xem.plo", "0", "0"));


        setStage(stage);
 
        // try {
        //     stage.setScene(new Scene(loadFXML("views/resultCenterRegister")));
        //     stage.show();
        // } catch (IOException e) {
        //     // Auto-generated catch block
        //     e.printStackTrace();
        // }

        loginView();
    }

    public static void loginView () {

        currentViewFxmlFile = (
            access == Profile.Administrator ? "login/provisory_adm" : 
            access == Profile.Gestor ? "login/provisory_ges" : 
            "login/provisory_col"
        );
        
        try {
            scene = new Scene(loadFXML(currentViewFxmlFile));

            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            System.out.println("App.loginView() -- Erro!");
            ex.printStackTrace();
        }

    }

    static void loadBase () throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("base.fxml"));
        stage.setScene(new Scene(loader.load()));
        baseController = loader.getController();

        baseController.getLb_currentUser().setText("Logado como " + Authentication.getCurrentUser().getNome());
    }

    public static void changeView (String newViewFxmlFile) {
        try {

            loadBase();

            Parent module = loadFXML(newViewFxmlFile);
            baseController.getAp_content().getChildren().add(module);
    
            for (View view : ViewsManager.getViews()) {

                FXMLLoader viewButtonLoader = new FXMLLoader(App.class.getResource("templates/viewButtonTemplate.fxml"));

                baseController.getVb_views().getChildren().add(viewButtonLoader.load());

                ViewButtonController viewButtonTemplateController = viewButtonLoader.getController();

                String buttonViewFxmlFile = view.getFxmlFileName();

                viewButtonTemplateController.setView(buttonViewFxmlFile);
                viewButtonTemplateController.setText(view.getDisplayName());

                if (buttonViewFxmlFile.equals(currentViewFxmlFile)) viewButtonTemplateController.setDisable(false);
                if (buttonViewFxmlFile.equals(newViewFxmlFile)) viewButtonTemplateController.setDisable(true);

            }

            currentViewFxmlFile = newViewFxmlFile;

        } catch (Exception ex) {
            System.out.println("App.changeView() -- Erro!");
            ex.printStackTrace();
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