
package com.cqu.aisr;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import Controllers.Enum.CSVConst;
import Controllers.Enum.Department;
import Controllers.Enum.Route;
import Controllers.Enum.Rule;
import Controllers.Enum.Status;
import Controllers.Helpers.UIHelper;
import Controllers.Services.AuthService;
import Controllers.Services.PersistsService;
import Controllers.Services.RouteService;
import Controllers.Services.Validation;
import Models.Applicant;
import Models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author kasun
 */
public class ItemController implements Initializable {

    private static int count;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label edu;
    @FXML
    private Button approveBtn;

    private Applicant applicant;

    @FXML
    private ChoiceBox<String> deptAssign;
    @FXML
    private Button editBtn;
    @FXML
    private Button seeMoreBtn;

    private HashMap<String, HashMap> editformData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deptAssign.setItems(Department.getValues());

        // Add listener to choice box
        deptAssign.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PersistsService.get().updateApplicant(applicant, CSVConst.DEPT, newValue);
            }
        });
        UIHelper.setElementsVisible(AuthService.get().user().isManager(), deptAssign);

        editBtn.setOnAction(e -> {
            editformData = new HashMap<String, HashMap>();
            setDataForEdit();
        });
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setData(Model model) {
        count();
        if (model instanceof Applicant) {
            Applicant ap = (Applicant) model;
            setApplicant(ap);
            name.setText(ap.getFirstName());
            id.setText("#" + count);
            address.setText(ap.getAddress());
            deptAssign.setValue(ap.getDepartment());
            edu.setText(ap.getEdu());
            if (!ap.isApproved() && AuthService.get().user().isManager()) {
                approveBtn.setVisible(Boolean.TRUE);
            } else {
                approveBtn.setVisible(Boolean.FALSE);
            }
        }
    }

    private void setDataForEdit() {
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
        row = appendTextInput(root, applicant.getUsername(), "Username:", "username", row);
        row = appendTextInput(root, applicant.getPassword(), "Password:", "password", row);

        // Create alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit details");
        alert.setHeaderText("Edit user John:");
        alert.getDialogPane().setContent(root);
        alert.getDialogPane().setPrefSize(600, 600);

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
                PersistsService.get().updateApplicant(applicant, CSVConst.USERNAME, getInput("username").getText());
                PersistsService.get().updateApplicant(applicant, CSVConst.PASSWORD, getInput("password").getText());
                System.out.println("Applicant Updated");
                RouteService.redirectTo(Route.APPLICATION);
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

    private TextField getInput(String field) {
        return (TextField) editformData.get(field).get("input");
    }

    private Label getValidationForField(String field) {
        Label l = (Label) editformData.get(field).get("validation");
        l.setWrapText(true);
        return l;
    }

    public static void count() {
        count++;
    }

    public static void resetCount() {
        count = 0;
    }

    @FXML
    private void approve(ActionEvent event) {
        approveBtn.setVisible(false);
        applicant.setStatus(Status.APPROVED.getValue());

        HashMap<CSVConst, String> updatedata = new HashMap<>();
        updatedata.put(CSVConst.STATUS, Status.APPROVED.getValue());
        PersistsService.get().updateApplicant(applicant, updatedata);
        System.out.println(applicant.getCSV());
    }

    @FXML
    private void seeMore(ActionEvent event) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setWidth(600);
        informationAlert.setTitle("Applicant Details");
        informationAlert.setContentText(
                "First Name: " + applicant.getFirstName() + "\n" +
                        "Last Name: " + applicant.getLastName() + "\n" +
                        "Email : " + applicant.getEmail() + "\n" +
                        "Phone : " + applicant.getPhone() + "\n" +
                        "Address : " + applicant.getAddress() + "\n" +
                        "Created at : " + applicant.getCreatedAt() + "\n" +
                        "Created branch : " + applicant.getCreatedBranch() + "\n" +
                        "Created by (staff id): " + applicant.getCreatedBy() + "\n" +
                        "Education: " + applicant.getEdu() + "\n");
        informationAlert.showAndWait();
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
}
