
package com.cqu.aisr;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import Controllers.Enum.Branch;
import Controllers.Enum.CSVConst;
import Controllers.Enum.Status;
import Controllers.Services.AuthService;
import Controllers.Services.PersistsService;
import Models.Applicant;
import Models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

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
    private Button assignToBranch;

    @FXML
    private ChoiceBox<String> branchAssign;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        branchAssign.setItems(Branch.getValues());

        // Add listener to choice box
        branchAssign.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Applicant Branch Before: " + applicant.getBranch());
            if (newValue != null) {
                PersistsService.get().updateApplicant(applicant, CSVConst.BRANCH, newValue);
                System.out.println("Selected Branch: " + newValue);
                System.out.println("Applicant Branch After: " + applicant.getBranch());
            }
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
            branchAssign.setValue(ap.getBranch().trim());
            edu.setText(ap.getEdu());
            if (!ap.isApproved() && AuthService.get().user().isManager()) {
                approveBtn.setVisible(Boolean.TRUE);
            } else {
                approveBtn.setVisible(Boolean.FALSE);
            }
            System.out.println("BRANCH TO SET:==========>" + ap.getBranch());
        }
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

}
