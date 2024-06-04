/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Enum.Errors;
import Controllers.Enum.Roles;
import Controllers.Enum.Route;
import Controllers.Helpers.Helper;
import Controllers.Helpers.UIHelper;
import Controllers.Services.AuthenticateService;
import Controllers.Services.RouteService;
import Controllers.Services.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class LoginController implements Initializable {

    @FXML
    private Button submitBtn;
    @FXML
    private TextField username;
    private ImageView usernameOK;
    private ImageView passwordOK;
    @FXML
    private PasswordField password;
    @FXML
    private Button registerAsAdmin;
    @FXML
    private Button registerMngmtBtn;
    @FXML
    private Label validationUserName;
    @FXML
    private Label validationPassword;
    @FXML
    private Label authFailedLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.setElementsVisible(
                Boolean.FALSE,
                validationPassword,
                validationUserName,
                usernameOK,
                passwordOK,
                authFailedLbl);
    }

    @FXML
    private void submitForm(ActionEvent event) 
    {
        if (AuthenticateService.authenticate(Helper.getText(username), Helper.getText(password))) {
            UIHelper.setElementsVisible(Boolean.FALSE, authFailedLbl);
            RouteService.redirectTo(Route.DASHBOARD);
        } else {
            UIHelper.setElementsVisible(Boolean.TRUE, authFailedLbl);
            Validation.setInvalidLabel(authFailedLbl, Errors.AUTH_FAILD.getMessage());
        }
    }

    @FXML
    private void registerAsAdmin(ActionEvent event) throws IOException {
        System.out.println("Clicked register as admin button");
        App.setRegistrationMode(Roles.ADMIN);
        RouteService.redirectTo(Route.REGISTRATION);
    }

    @FXML
    private void registerAsManagemement(ActionEvent event) throws IOException {
        System.out.println("Clicked register as management button");
        App.setRegistrationMode(Roles.MANAGEMENT);
        RouteService.redirectTo(Route.REGISTRATION);
    }
    
    @FXML
    private void editRecruit(ActionEvent event) throws IOException {
        System.out.println("Clicked edit recruit button");
        RouteService.redirectTo(Route.EDITAPPLICANT);
    }


}
