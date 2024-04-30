/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import java.net.URL;
import java.util.ResourceBundle;

import Enum.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class VacanciesController extends MenuController implements Initializable {

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
    private VBox applicationSubNav;
    @FXML
    private Label staffSideMenu;
    @FXML
    private Label reportsSideMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapMenuRoute(homeSideMenu, Route.DASHBOARD);
        mapMenuRoute(applicationsSideMenu, Route.APPLICATION);
        mapMenuRoute(vacanciesSideMenu, Route.VACANCIES);
        mapMenuRoute(staffSideMenu, Route.STAFF);
        mapMenuRoute(reportsSideMenu, Route.REPORT);
        mapMenuRoute(logOutMenuItem, Route.LOGOUT);
    }

}
