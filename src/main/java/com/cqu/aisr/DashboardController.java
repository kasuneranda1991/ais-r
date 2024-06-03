/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Enum.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class DashboardController extends BaseController implements Initializable {

    @FXML
    private Label loggedInUserlbl;
    @FXML
    private Label logOutMenuItem;
    @FXML
    private Label applicationMenuItem;
    @FXML
    private Label vacanciesMenuItem;
    @FXML
    private Label staffMenuItem;
    @FXML
    private Label reportMenuItem;
    @FXML
    private Label chartMenuItem;
    @FXML
    private Label profileMenuItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateUser(loggedInUserlbl);
        mapMenuRoute(applicationMenuItem, Route.APPLICATION);
        mapMenuRoute(vacanciesMenuItem, Route.VACANCIES);
        mapMenuRoute(staffMenuItem, Route.STAFF);
        mapMenuRoute(reportMenuItem, Route.REPORT);
        mapMenuRoute(chartMenuItem, Route.CHART);
        mapMenuRoute(logOutMenuItem, Route.LOGOUT);
        mapMenuRoute(profileMenuItem, Route.REGISTRATION);
    }

}
