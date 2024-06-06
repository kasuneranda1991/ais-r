package com.cqu.aisr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Enum.Branch;
import Controllers.Enum.CSVConst;
import Controllers.Enum.EmploymentType;
import Controllers.Enum.ManagementLevel;
import Controllers.Enum.Roles;
import Controllers.Enum.Route;
import Controllers.Enum.Rule;
import Controllers.Helpers.Helper;
import Controllers.Helpers.UIHelper;
import Controllers.Services.AuthService;
import Controllers.Services.AuthenticateService;
import Controllers.Services.PersistsService;
import Controllers.Services.RouteService;
import Controllers.Services.Validation;
import Models.Administration;
import Models.Management;
import Models.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button submitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mgmt_lvl.setItems(ManagementLevel.getValues());
        employment_type.setItems(EmploymentType.getValues());
        branch.setItems(Branch.getValues());
        Staff authUser = AuthService.get().user();
        if (authUser != null) {
            System.out.println("Authenticated User presented");
            branch.setValue(authUser.getBranch());
            fName.setText(authUser.getFirstName());
            lName.setText(authUser.getLastName());
            address.setText(authUser.getAddress());
            phone.setText(String.valueOf(authUser.getPhone()));
            email.setText(authUser.getEmail());
            username.setText(authUser.getUsername());
            
            Title.setText("Update staff member details");
            if (Roles.ADMIN.getValue().equals(authUser.getRole())) {
                System.out.println("Set Edit value for Admin");
                registrationType = Roles.ADMIN;
                Administration admin = (Administration) authUser;
                employment_type.setValue(admin.getEmployment_type());
                System.out.println(admin.getBranch());
                UIHelper.setElementsVisible(
                        Boolean.FALSE,
                        LblForInputMgmtLvl,
                        mgmt_lvl);
            } else if (Roles.MANAGEMENT.getValue().equals(authUser.getRole())) {
                System.out.println("Set Edit value for Management");
                registrationType = Roles.MANAGEMENT;
                Management mgmt = (Management) authUser;
                branch.setValue(mgmt.getBranch());
                mgmt_lvl.setValue(mgmt.getManagement_lvl());
                UIHelper.setElementsVisible(
                        Boolean.FALSE,
                        LblForInputEmType,
                        employment_type);
            }
            
        } else {
            System.out.println("Registration mode for new registration: " + registrationType.getValue());
            employment_type.setValue(EmploymentType.FULL_TIME.getValue());
            mgmt_lvl.setValue(ManagementLevel.SUPERVISOR.getValue());
            branch.setValue(Branch.MEL.getValue());
            
            if (registrationType == Roles.ADMIN) {
                Title.setText("Adminstration staff registration");
                UIHelper.setElementsVisible(
                        Boolean.FALSE,
                        LblForInputMgmtLvl,
                        mgmt_lvl);
            } else if (registrationType == Roles.MANAGEMENT) {
                Title.setText("Management staff registration");
                UIHelper.setElementsVisible(
                        Roles.ADMIN == registrationType,
                        LblForInputEmType,
                        employment_type);
            }
            
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
        
        isFormValid &= validation.validate(fName, Rule.NOT_NULL, fNameLbl, fNameOK);
        isFormValid &= validation.validate(lName, Rule.NOT_NULL, lNameLbl, lNameOK);
        isFormValid &= validation.validate(address, Rule.NOT_NULL, addressLbl, addressOK);
        isFormValid &= validation.validate(phone, Rule.PHONE, phoneLbl, phoneOK);
        isFormValid &= validation.validate(email, Rule.EMAIL, emailLbl, emailOK);
        isFormValid &= validation.validate(username, Rule.NOT_NULL, usernameLbl, usernameOK);
        if (AuthService.get().user() != null) {
            System.out.println("Update mode activated");
            if (!(Helper.getText(password)).isEmpty()) {
                System.out.println("Update password ");
                isFormValid &= validation.validate(password, Rule.PASSWORD, passwordLbl, passwordOK);
                isFormValid &= validation.validate(confirm, Rule.PASSWORD_CONFIRM, confirmLbl, passwordConfOK, password);
            }
        } else {
            isFormValid &= validation.validate(password, Rule.PASSWORD, passwordLbl, passwordOK);
            isFormValid &= validation.validate(confirm, Rule.PASSWORD_CONFIRM, confirmLbl, passwordConfOK, password);
        }
        if (isFormValid) {
            if (AuthService.get().user() != null) {
                Staff st = AuthService.get().user();
                PersistsService.get().updateStaff(st, CSVConst.FNAME, Helper.getText(fName));
                PersistsService.get().updateStaff(st, CSVConst.LNAME, Helper.getText(lName));
                PersistsService.get().updateStaff(st, CSVConst.ADDRESS, Helper.getText(address));
                PersistsService.get().updateStaff(st, CSVConst.PHONE, Helper.getText(phone));
                PersistsService.get().updateStaff(st, CSVConst.EMAIL, Helper.getText(email));
                PersistsService.get().updateStaff(st, CSVConst.USERNAME, Helper.getText(username));
                PersistsService.get().updateStaff(st, CSVConst.BRANCH, branch.getValue());
                if (!(Helper.getText(password)).isEmpty()) {
                    PersistsService.get().updateStaff(st, CSVConst.PASSWORD, Helper.getText(password));
                }
                if (AuthService.get().user() instanceof Administration) {
                    PersistsService.get().updateStaff(st, CSVConst.EM_TYPE, employment_type.getValue());
                }
                
                if (AuthService.get().user() instanceof Management) {
                    PersistsService.get().updateStaff(st, CSVConst.MGMT_LVL, mgmt_lvl.getValue());
                }
                RouteService.redirectTo(Route.DASHBOARD);
            } else {
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
                    user.setBranch(branch.getValue());
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
                try {
                    PersistsService.get().addStaff(user);
                    AuthenticateService.authenticateUser(user);
                    RouteService.redirectToWithMessage(Route.DASHBOARD, "You have been registed as " + user.getRole());
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Exit");
                }
                
            }
        }
        
    }
    
    @FXML
    private void redirectBack(ActionEvent event) throws IOException {
        if (AuthService.get().user() != null) {
            RouteService.redirectTo(Route.DASHBOARD);
        } else {
            App.setRoot("Login");            
        }
        
    }
    
}
