package Services;

import Enum.Errors;
import Enum.Regex;
import Helpers.UIHelper;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * This class responsible for the all the form related validations
 * @author kasun eranda - 12216898
 */
public class Validation {

    private static String INVALID_INPUT = "#ff7a8c"; // invalid input color
    private static String VALID_INPUT = "#63db00"; // valid input color
    private String error; // validation error

    /**
     * Validate user inputs based on the validation rule given
     */
    public Boolean validate(TextField input, String validation_rule, Label invalidLbl, ImageView valid,
            TextField... inputs) {
        Boolean isValid = false;
        if (!isNotNull(input) && validation_rule != "NotNull") {
            this.setErrorMessage(Errors.IS_NOT_NULL.getMessage());
        } else {
            switch (validation_rule) {
                case "Numeric":
                    isValid = isNumeric(input);
                    this.setErrorMessage(Errors.IS_NUMERIC.getMessage());
                    break;
                case "NotNull":
                    isValid = isNotNull(input);
                    this.setErrorMessage(Errors.IS_NOT_NULL.getMessage());
                    break;
                case "Email":
                    isValid = isEmail(input);
                    this.setErrorMessage(Errors.IS_EMAIL.getMessage());
                    break;
                case "Phone":
                    isValid = isPhoneNumber(input);
                    this.setErrorMessage(Errors.IS_PHONE.getMessage());
                    break;
                case "Password":
                    isValid = isValidPassword(input);
                    this.setErrorMessage(Errors.PASSWORD.getMessage());
                    break;
                case "PasswordConfirm":
                if (inputs.length > 0) {
                    isValid = isPasswordConfirmed(input,inputs[0]); 
                    this.setErrorMessage(Errors.PASSWORD_CONFIRMATION.getMessage());
                } 
                    break;
                default:
                    break;
            }
        }

        if (!isValid) {
            this.setInvalidInputStyle(input, invalidLbl, valid);
        } else {
            this.setValidInputStyle(input, invalidLbl, valid);
        }
        return isValid;
    }

    public Boolean isNumeric(TextField input) {
        return input.getText().matches(Regex.DIGIT.getVal());
    }

    public Boolean isEmail(TextField input) {
        return input.getText().matches(Regex.EMAIL.getVal());
    }

    public Boolean isPhoneNumber(TextField input) {
        return input.getText().matches(Regex.PHONE_NUMBER.getVal());
    }

    public Boolean isValidPassword(TextField input) {
        return input.getText().matches(Regex.PASSWORD_MIN_LENGTH_8.getVal());
    }
    
    public Boolean isPasswordConfirmed(TextField confirm, TextField password) {
        return confirm.getText().equals(password.getText());
    }

    public Boolean isNotNull(TextField input) {
        return !(input.getText().isEmpty());
    }

    private void setInvalidInputStyle(TextField input, Label lbl, ImageView success) {
        input.setStyle("-fx-border-color: " + INVALID_INPUT + " ;  \n"
                + " -fx-border-width: 2px ;  ");

        UIHelper.setElementsVisible(Boolean.TRUE, lbl);
        UIHelper.setElementsVisible(Boolean.FALSE, success);
        lbl.setText(this.getValidationMessage());
        lbl.setStyle("-fx-text-fill: " + INVALID_INPUT + ";");

    }

    private void setValidInputStyle(TextField input, Label lbl, ImageView success) {
        input.setStyle("-fx-border-color: " + VALID_INPUT + " ;  \n"
                + " -fx-border-width: 2px ;  ");
        UIHelper.setElementsVisible(Boolean.FALSE, lbl);
        UIHelper.setElementsVisible(Boolean.TRUE, success);
    }

    private void setErrorMessage(String message) {
        this.error = message;
    }

    public String getValidationMessage() {
        return error;
    }
}
