package com.cqu.aisr;

import java.io.IOException;

import Controllers.Enum.CSVConst;
import Controllers.Enum.Config;
import Controllers.Enum.Roles;
import Controllers.Enum.Route;
import Controllers.Helpers.CSV;
import Controllers.Services.AuthenticateService;
import Controllers.Services.NotificationService;
import Controllers.Services.PersistsService;
import Controllers.Services.RouteService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {

    private Timeline timeline;

    private static Scene scene;

    private static Roles registrationMode = Roles.ADMIN;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"), 1920, 1000);
        scene.setOnMouseMoved(e -> resetTimeline());
        scene.setOnKeyTyped(e -> resetTimeline());
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

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(Integer.parseInt(Config.INACTIVITY.getValue())), event -> {
                    if (RouteService.current != null && !RouteService.current.equals(Route.LOGIN)) {
                        Platform.runLater(() -> {
                            NotificationService.message("You are Inactive. You have been logged out.");
                            AuthenticateService.logout();
                        });
                    }

                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void resetTimeline() {
        timeline.stop();
        timeline.playFromStart();
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