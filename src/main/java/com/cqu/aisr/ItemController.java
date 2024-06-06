package com.cqu.aisr;

import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Tooltip;
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
    @FXML
    private TextField secondaryDept;
    @FXML
    private Button sendTokenButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deptAssign.setItems(Department.getValues());

        // Add listener to choice box
        deptAssign.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (!(newValue == null)) {
                System.out.println("Primary Deparment changed form " + oldValue + " to " + newValue);
                PersistsService.get().updateApplicant(applicant, CSVConst.DEPT, newValue);
            }
        });

        secondaryDept.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obsr, String old, String nevDept) {

                if (!(nevDept == null) && nevDept.endsWith(",")) {
                    System.out.println("Secondary department changed : " + nevDept.replace(",", " ").trim());
                    PersistsService.get().updateApplicant(applicant, CSVConst.SECONDARY_DEPTS,
                            nevDept.replace(",", " ").trim());
                }
            }
        });

        UIHelper.setElementsVisible(AuthService.get().user().isManager(), deptAssign);

        editBtn.setOnAction(e -> {
            editformData = new HashMap<String, HashMap>();
            setDataForEdit();
        });
        
        final Tooltip tooltp = new Tooltip();
        tooltp.setText("Please add the secondary department by separating comma ex.Software,");
        
        secondaryDept.setTooltip(tooltp);
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
            secondaryDept.setText(ap.getSecondaryDepartments());
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
        row = appendTextInput(root, applicant.getWorkingEx(), "Years of working experience:", "workEx", row);
        row = appendTextInput(root, applicant.getOther(), "Other Experience:", "otherEx", row);
        row = appendTextInput(root, applicant.getUsername(), "Username:", "username", row);
        row = appendTextInput(root, applicant.getPassword(), "Password:", "password", row);

        // Create alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit details");
        alert.setHeaderText("Edit user John:");
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
                "First Name: " + applicant.getFirstName() + "\n"
                + "Last Name: " + applicant.getLastName() + "\n"
                + "Email : " + applicant.getEmail() + "\n"
                + "Phone : " + applicant.getPhone() + "\n"
                + "Address : " + applicant.getAddress() + "\n"
                + "Created at : " + applicant.getCreatedAt() + "\n"
                + "Created branch : " + applicant.getCreatedBranch() + "\n"
                + "Created by (staff id): " + applicant.getCreatedBy() + "\n"
                + "Education: " + applicant.getEdu() + "\n");
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

    @FXML
    public void sendToken(ActionEvent event) {

        // Fake SMTP server information
        String host = "localhost";
        int port = 25;
        String subject = "AIS-R Token";

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", String.valueOf(port));

        Session session = Session.getDefaultInstance(properties);

        try {

            String token = generateToken();

            String body = "Your Token is : " + token + "\n\n"
                    + "Please use this token to complete your registration. \n\n"
                    + "Thank you. \n\n"
                    + "AIS-R Team. \n\n";

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("sender@example.com"));

            String to = applicant.getEmail();

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject);

            message.setText(body);

            Transport.send(message);

            applicant.setOneTimeToken(token);
            HashMap<CSVConst, String> updatedata = new HashMap<>();

            // update applicant with token in mysql database
            updatedata.put(CSVConst.ONE_TIME_TOKEN, token);

            PersistsService.get().updateApplicant(applicant, updatedata);
            
            System.out.println(applicant.getCSV());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Token Sent");
            alert.setHeaderText("Token sent to " + to);
            alert.setContentText("Token has been sent to " + to + ". Please check email.");
            alert.showAndWait();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static String generateToken() {

        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int TOKEN_LENGTH = 10;
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            token.append(randomChar);
        }

        return token.toString();
    }

}
