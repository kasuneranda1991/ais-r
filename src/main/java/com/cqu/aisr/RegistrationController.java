package com.cqu.aisr;

import Helpers.UIHelper;
import Services.Validation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private Button submitBtn;
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
    private Tooltip fNameToolTip;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fNameLbl.setVisible(false);
        fNameOK.setVisible(false);
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

        System.out.println(isFormValid);

    }

}
