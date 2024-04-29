package com.cqu.aisr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Enum.Branch;
import Enum.EmploymentType;
import Enum.ManagementLevel;
import Enum.Roles;
import Helpers.Helper;
import Helpers.UIHelper;
import Models.Administration;
import Models.Management;
import Models.Staff;
import Services.PersistsService;
import Services.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class for register form validation
 *
 * @author
 */
public class RegistrationController implements Initializable {

    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private Label fNameLbl;
    @FXML
    private Label lNameLbl;
    @FXML
    private Label addressLbl;
    @FXML
    private Label phoneLbl;
    @FXML
    private Label emailLbl;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label confirmLbl;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirm;
    @FXML
    private ImageView fNameOK;
    @FXML
    private ImageView lNameOK;
    @FXML
    private ImageView addressOK;
    @FXML
    private ImageView phoneOK;
    @FXML
    private ImageView emailOK;
    @FXML
    private ImageView usernameOK;
    @FXML
    private ImageView passwordOK;
    @FXML
    private ImageView passwordConfOK;
    @FXML
    private TextField address;
    @FXML
    private ChoiceBox<String> employment_type;
    @FXML
    private ChoiceBox<String> mgmt_lvl;
    @FXML
    private ChoiceBox<String> branch;
    @FXML
    private Label LblForInputEmType;
    @FXML
    private Label LblForInputMgmtLvl;
    @FXML
    private Label LblForInputBrnch;

    private Roles registrationType = App.getRegistrationMode();

    private Staff user;
    @FXML
    private Label Title;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        employment_type.setItems(EmploymentType.getValues());
        employment_type.setValue(EmploymentType.FULL_TIME.getValue());

        mgmt_lvl.setItems(ManagementLevel.getValues());
        mgmt_lvl.setValue(ManagementLevel.SUPERVISOR.getValue());

        branch.setItems(Branch.getValues());
        branch.setValue(Branch.MEL.getValue());

        if (registrationType == Roles.ADMIN) {
            Title.setText("Adminstration staff registration");
            UIHelper.setElementsVisible(
                    Boolean.FALSE,
                    LblForInputBrnch,
                    LblForInputMgmtLvl,
                    branch,
                    mgmt_lvl);
        } else if (registrationType == Roles.MANAGEMENT) {
                        Title.setText("Management staff registration");
            UIHelper.setElementsVisible(
                    Roles.ADMIN == registrationType,
                    LblForInputEmType,
                    employment_type);
        }

        UIHelper.setElementsVisible(
                Boolean.FALSE,
                fNameLbl,
                lNameLbl,
                addressLbl,
                phoneLbl,
                emailLbl,
                usernameLbl,
                passwordLbl,
                confirmLbl,
                fNameOK,
                lNameOK,
                addressOK,
                phoneOK,
                emailOK,
                usernameOK,
                passwordOK,
                passwordConfOK);
    }

    /**
     * Handle register form submission
     *
     * @param event
     */
    @FXML
    private void submitForm(ActionEvent event) {

        Validation validation = new Validation();
        Boolean isFormValid = Boolean.TRUE;

        isFormValid &= validation.validate(fName, "NotNull", fNameLbl, fNameOK);
        isFormValid &= validation.validate(lName, "NotNull", lNameLbl, lNameOK);
        isFormValid &= validation.validate(address, "NotNull", addressLbl, addressOK);
        isFormValid &= validation.validate(phone, "Phone", phoneLbl, phoneOK);
        isFormValid &= validation.validate(email, "Email", emailLbl, emailOK);
        isFormValid &= validation.validate(username, "NotNull", usernameLbl, usernameOK);
        isFormValid &= validation.validate(password, "Password", passwordLbl, passwordOK);
        isFormValid &= validation.validate(confirm, "PasswordConfirm", confirmLbl, passwordConfOK, password);

        if (isFormValid) {

            if (registrationType == Roles.ADMIN) {
                user = new Administration(
                        Helper.getText(fName),
                        Helper.getText(lName),
                        Helper.getText(address),
                        Helper.getInt(phone),
                        Helper.getText(email),
                        Helper.getText(username),
                        Helper.getText(password),
                        employment_type.getValue());
            } else if (registrationType == Roles.MANAGEMENT) {
                user = new Management(
                        Helper.getText(fName),
                        Helper.getText(lName),
                        Helper.getText(address),
                        Helper.getInt(phone),
                        Helper.getText(email),
                        Helper.getText(username),
                        Helper.getText(password),
                        mgmt_lvl.getValue(),
                        branch.getValue());
            }
            PersistsService.get().addStaff(user);
            System.out.println(PersistsService.get().staffData().size());
        }

    }

    @FXML
    private void redirectBack(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

}
