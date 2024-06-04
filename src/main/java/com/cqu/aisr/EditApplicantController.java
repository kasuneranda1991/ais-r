/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cqu.aisr;

import Controllers.Enum.CSVConst;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Enum.Errors;
import Controllers.Enum.Roles;
import Controllers.Enum.Route;
import Controllers.Enum.Rule;
import static Controllers.Enum.Rule.NOT_NULL;
import Controllers.Helpers.Helper;
import Controllers.Helpers.UIHelper;
import Controllers.Services.AuthService;
import Controllers.Services.AuthenticateService;
import Controllers.Services.PersistsService;
import Controllers.Services.RouteService;
import Controllers.Services.Validation;
import Models.Applicant;
import Models.User;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class EditApplicantController implements Initializable {

    @FXML
    private Button submitBtn;
    @FXML
    private TextField token;
    @FXML
    private PasswordField password;
    @FXML
    private Label validationToken;
    @FXML
    private Label authFailedLbl;

    private Applicant applicant;

    private HashMap<String, HashMap> editformData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.setElementsVisible(
                Boolean.FALSE,
                validationToken,
                authFailedLbl);
    }

    @FXML
    private void submitForm(ActionEvent event) {
        System.out.println(Helper.getText(token));
        System.out.println("Akila");

        Validation validation = new Validation();
        Boolean isFormValid = Boolean.TRUE;

//        isFormValid &= validation.validate(token, Rule.NOT_NULL, authFailedLbl);
        if (isFormValid) {

            Applicant applicant = AuthenticateService.getApplicantByToken(Helper.getText(token));
            if (applicant != null) {
                System.out.println("Applicant found");
                UIHelper.setElementsVisible(
                        Boolean.FALSE,
                        authFailedLbl);
                editformData = new HashMap<String, HashMap>();
                setDataForEdit(applicant);
            } else {
                System.out.println("Applicant not found");
                UIHelper.setElementsVisible(
                        Boolean.TRUE,
                        authFailedLbl);
            }
        }
    }

    private void setDataForEdit(Applicant applicant) {
        int row = -1;

        // Create a layout
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setHgap(10);
        row = appendTextInput(root, applicant.getFirstName(), "First Name:", "fname", row);
        row = appendTextInput(root, applicant.getLastName(), "Last Name:", "lname", row);
        row = appendTextInput(root, applicant.getAddress(), "Address:", "address", row);
        row = appendTextInput(root, String.valueOf(applicant.getPhone()), "Phone:", "phone", row);
        row = appendTextInput(root, applicant.getEmail(), "Email:", "email", row);
        row = appendTextInput(root, applicant.getWorkingEx(), "Years of working experience:", "workEx", row);
        row = appendTextInput(root, applicant.getOther(), "Other Experience:", "otherEx", row);
        row = appendTextInput(root, applicant.getUsername(), "Username:", "username", row);
        row = appendTextInput(root, applicant.getPassword(), "Password:", "password", row);

        // Create alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit details");
        alert.setHeaderText("Edit user");
        alert.getDialogPane().setContent(root);
        alert.getDialogPane().setPrefSize(600, 750);

        // Show the alert and wait for user response
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {

            Validation validation = new Validation();
            Boolean isFormValid = Boolean.TRUE;
            isFormValid &= validation.validate(getInput("fname"), Rule.NOT_NULL, getValidationForField("fname"));
            isFormValid &= validation.validate(getInput("lname"), Rule.NOT_NULL, getValidationForField("lname"));
            isFormValid &= validation.validate(getInput("email"), Rule.EMAIL, getValidationForField("email"));
            isFormValid &= validation.validate(getInput("phone"), Rule.PHONE, getValidationForField("phone"));
            isFormValid &= validation.validate(getInput("address"), Rule.NOT_NULL, getValidationForField("address"));
            isFormValid &= validation.validate(getInput("username"), Rule.NOT_NULL, getValidationForField("username"));
            isFormValid &= validation.validate(getInput("password"), Rule.PASSWORD, getValidationForField("password"));

            if (isFormValid) {
                PersistsService.get().updateApplicant(applicant, CSVConst.FNAME, getInput("fname").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.LNAME, getInput("lname").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.EMAIL, getInput("email").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.PHONE, getInput("phone").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.ADDRESS, getInput("address").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.WORKING_EX, getInput("workEx").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.OTHER, getInput("otherEx").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.USERNAME, getInput("username").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.PASSWORD, getInput("password").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.ONE_TIME_TOKEN, "Test123");
                System.out.println("Applicant Updated");

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Applicant updated successfully");
                successAlert.showAndWait();

                RouteService.redirectTo(Route.LOGIN);
            } else {
                event.consume();
                System.out.println("Invalid applicant update form");
            }
        });

        // Show the alert and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response != ButtonType.OK) {
                System.out.println("Cancelled");
            }
        });
    }

    private int appendTextInput(GridPane grid, String placeholder, String label, String variable, int row) {
        HashMap<String, Control> inputfields = new HashMap<String, Control>();
        // Create text input field
        TextField textField = UIHelper.input(placeholder);
        Label validationLabel = UIHelper.label(null);

        inputfields.put("input", textField);
        inputfields.put("validation", validationLabel);

        // Create labels
        Label emailLabel = UIHelper.label(label);
        validationLabel.setStyle("-fx-text-fill: red;");
        grid.addRow(++row, emailLabel, textField);
        grid.addRow(++row, validationLabel);
        editformData.put(variable, inputfields);
        return row;
    }

    private TextField getInput(String field) {
        return (TextField) editformData.get(field).get("input");
    }

    private Label getValidationForField(String field) {
        Label l = (Label) editformData.get(field).get("validation");
        l.setWrapText(true);
        return l;
    }

    @FXML
    private void redirectBack(ActionEvent event) throws IOException {
        if (AuthService.get().user() != null) {
            RouteService.redirectTo(Route.LOGIN);
        } else {
            App.setRoot("Login");
        }

    }

}
