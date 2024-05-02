/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import java.net.URL;
import java.util.ResourceBundle;

import Enum.Route;
import Models.User;
import Services.PersistsService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class ApplicationController extends BaseController implements Initializable {

    @FXML
    private Label loggedInUserlbl;
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
    private Label logOutMenuItem;
    @FXML
    private Label applicationCreateSubNav;
    @FXML
    private Label name;
    @FXML
    private VBox applicationsPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateUser(loggedInUserlbl);
        mapMenuRoute(homeSideMenu, Route.DASHBOARD);
        mapMenuRoute(applicationsSideMenu, Route.APPLICATION);
        mapMenuRoute(vacanciesSideMenu, Route.VACANCIES);
        mapMenuRoute(staffSideMenu, Route.STAFF);
        mapMenuRoute(reportsSideMenu, Route.REPORT);
        mapMenuRoute(logOutMenuItem, Route.LOGOUT);
        mapMenuRoute(applicationCreateSubNav, Route.APPLICATION_CREATE);
        ItemController.resetCount();
        for (User applicant : PersistsService.get().applicantsData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ListItem.fxml"));
                applicationsPane.getChildren().add(loader.load());
                ItemController controller = loader.getController();
                controller.setData(applicant);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
