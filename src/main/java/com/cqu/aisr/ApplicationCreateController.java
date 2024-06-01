/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Enum.EduQualification;
import Controllers.Enum.Route;
import Controllers.Enum.Rule;
import Controllers.Helpers.Helper;
import Controllers.Helpers.UIHelper;
import Controllers.Services.PersistsService;
import Controllers.Services.RouteService;
import Controllers.Services.Validation;
import Models.Applicant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class ApplicationCreateController extends BaseController implements Initializable {

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
    private Label validation1;
    @FXML
    private Label validation2;
    @FXML
    private Label validation3;
    @FXML
    private Label validation4;
    @FXML
    private Label validation5;
    @FXML
    private Label validation7;
    @FXML
    private Label validation8;
    @FXML
    private Label validation9;
    @FXML
    private Button createBtn;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private ChoiceBox<String> eduQualification;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm;
    @FXML
    private ImageView fNameOK;
    @FXML
    private ImageView lNameOK;
    @FXML
    private ImageView emailOK;
    @FXML
    private ImageView phoneOK;
    @FXML
    private ImageView addressOK;
    @FXML
    private ImageView usernameOK;
    @FXML
    private ImageView passwordOK;
    @FXML
    private ImageView confirmOK;
    @FXML
    private DatePicker interviewDate;
    @FXML
    private Label validation10;
    @FXML
    private ImageView dateOK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // interviewDate.setValue(LocalDate.now().plusDays(3));

        eduQualification.setItems(EduQualification.getValues());
        eduQualification.setValue(EduQualification.DIP.getValue());

        updateUser(loggedInUserlbl);
        mapMenuRoute(homeSideMenu, Route.DASHBOARD);
        mapMenuRoute(applicationsSideMenu, Route.APPLICATION);
        mapMenuRoute(vacanciesSideMenu, Route.VACANCIES);
        mapMenuRoute(staffSideMenu, Route.STAFF);
        mapMenuRoute(reportsSideMenu, Route.REPORT);
        mapMenuRoute(logOutMenuItem, Route.LOGOUT);

        UIHelper.setElementsVisible(
                Boolean.FALSE,
                validation1,
                validation2,
                validation3,
                validation4,
                validation5,
                validation7,
                validation8,
                validation9,
                validation10,
                fNameOK,
                lNameOK,
                emailOK,
                phoneOK,
                addressOK,
                usernameOK,
                passwordOK,
                dateOK,
                confirmOK);
        createBtn.setOnAction(e -> {
            doSubmit();
        });
    }

    public void doSubmit() {

        Validation validation = new Validation();
        Boolean isFormValid = Boolean.TRUE;

        isFormValid &= validation.validate(fname, Rule.NOT_NULL, validation1, fNameOK);
        isFormValid &= validation.validate(lname, Rule.NOT_NULL, validation2, lNameOK);
        isFormValid &= validation.validate(email, Rule.EMAIL, validation3, emailOK);
        isFormValid &= validation.validate(phone, Rule.PHONE, validation4, phoneOK);
        isFormValid &= validation.validate(address, Rule.NOT_NULL, validation5, addressOK);
        isFormValid &= validation.validate(username, Rule.NOT_NULL, validation7, usernameOK);
        isFormValid &= validation.validate(password, Rule.PASSWORD, validation8, passwordOK);
        isFormValid &= validation.validate(confirm, Rule.PASSWORD_CONFIRM, validation9, confirmOK, password);
        isFormValid &= validation.validate(interviewDate, Rule.DATE, validation10, dateOK);
        if (isFormValid) {
            Applicant applicant = new Applicant(
                    Helper.getText(fname),
                    Helper.getText(lname),
                    Helper.getText(address),
                    Helper.getText(email),
                    Helper.getInt(phone),
                    Helper.getText(username),
                    Helper.getText(password),
                    interviewDate.getValue(), eduQualification.getValue()); 
            PersistsService.get().addApplicant(applicant);
            RouteService.redirectToWithMessage(Route.APPLICATION, "Application created.");
        }
    }
}
