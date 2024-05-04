package com.cqu.aisr;

import java.io.IOException;

import javax.crypto.SecretKey;

import Controllers.Enum.CSVConst;
import Controllers.Enum.Roles;
import Controllers.Helpers.CSV;
import Controllers.Helpers.Security;
import Controllers.Services.PersistsService;
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
            CSV.setHeading("staff.csv", CSVConst.STAFF_CSV_HEADING.getValue());
        }
        if (!CSV.isFileExists("recruits.csv")) {
            CSV.createFile("recruits.csv");
            CSV.setHeading("recruits.csv", CSVConst.RECRUITS_CSV_HEADING.getValue());
        }

        try {
            PersistsService.get().mapCSVData("staff.csv", "recruits.csv");
            System.out.println(PersistsService.get().staffData().size());
        } catch (Exception e) {
            System.out.println(e);
        }
        SecretKey key = Security.getKey();
        System.out.println("key => " + key);
        String name = "+0FD8TPvAzXLcojPg9+J5g==\n" + //
                        "";
        String data_en = Security.encrypt(name, key);
        System.out.println("encypt:" + data_en);
        System.out.println("decrypt:" + Security.decrypt(data_en, key));

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