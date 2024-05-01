package com.cqu.aisr;

import java.io.IOException;

import Enum.Config;
import Enum.Roles;
import Helpers.CSV;
import Services.PersistsService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private static Roles registrationMode = Roles.ADMIN;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"), 1920, 1000);
        stage.setScene(scene);
        stage.show();

        if (!CSV.isFileExists("staff.csv")) {
            CSV.createFile("staff.csv");
            CSV.setHeading("staff.csv", Config.STAFF_CSV_HEADING.getValue());
        } else {
            PersistsService.get().mapStaffCSVData("staff.csv");
        }

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Roles getRegistrationMode() {
        return App.registrationMode;
    }

    public static void setRegistrationMode(Roles registrationMode) {
        App.registrationMode = registrationMode;
    }
}