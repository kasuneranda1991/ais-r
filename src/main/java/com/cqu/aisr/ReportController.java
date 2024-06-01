/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Enum.Route;
import Controllers.Services.PersistsService;
import Models.Applicant;
import Models.Staff;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class ReportController extends BaseController implements Initializable {

    @FXML
    private Label loggedInUserlbl;
    @FXML
    private Label logOutMenuItem;
    @FXML
    private Label homeSideMenu;
    @FXML
    private Label applicationsSideMenu;
    @FXML
    private Label vacanciesSideMenu;
    @FXML
    private Label staffSideMenu;
    @FXML
    private Label reportsSideMenu;
    @FXML
    private VBox applicationSubNav;
    @FXML
    private Tab totalApplicants, staffCount, applicantsOnDept;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalApplicants.setContent(
                setReport("Total applicants: " +
                        PersistsService.get().applicantsData().size()));
        staffCount.setContent(
                setReport("Total Staff: " +
                        PersistsService.get().staffData().size() + "\n" +
                        "Total Administrators: " + Staff.totalAdmins() + "\n" +
                        "Total Managers: " + Staff.totalManagers()));

        applicantsOnDept.setContent(setReport(Applicant.stats()));
        updateUser(loggedInUserlbl);
        mapMenuRoute(homeSideMenu, Route.DASHBOARD);
        mapMenuRoute(applicationsSideMenu, Route.APPLICATION);
        mapMenuRoute(vacanciesSideMenu, Route.VACANCIES);
        mapMenuRoute(staffSideMenu, Route.STAFF);
        mapMenuRoute(reportsSideMenu, Route.REPORT);
        mapMenuRoute(logOutMenuItem, Route.LOGOUT);
    }

    public Label setReport(String content) {
        Label label = new Label(content);
        label.setStyle(
                "-fx-background-color: #022c56;" +
                        "-fx-background-radius: 5, 4, 3, 5;" +
                        "-fx-background-radius: 5, 4, 3, 5;" +
                        "-fx-background-insets: 0, 1, 2, 0;" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 5, 0, 0, 1);" +
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 15px;" +
                        "-fx-padding: 10 20 10 20;");
        return label;
    }

}
