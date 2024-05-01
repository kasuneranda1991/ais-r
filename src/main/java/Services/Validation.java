package Services;

import java.time.LocalDate;

import Enum.Errors;
import Enum.Regex;
import Enum.Rule;
import Helpers.UIHelper;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class responsible for the all the form related validations
 * 
 * @author kasun eranda - 12216898
 */
public class Validation {

    private static String INVALID_INPUT = "#ff7a8c"; // invalid input color
    private static String VALID_INPUT = "#63db00"; // valid input color
    private String error; // validation error

    public Boolean validate(Node node, Rule rule, Label invalid) {
        Boolean isValid = false;
        if (!isNotNull(node) && rule != Rule.NOT_NULL) {
            this.setErrorMessage(Errors.IS_NOT_NULL.getMessage());
        } else {
            switch (rule) {
                case Rule.DATE:
                    isValid = this.isDate(node);
                    break;
                default:
                    break;
            }
        }
        updateNode(isValid, node, invalid);
        return isValid;

    }

    public Boolean validate(Node node, Rule rule, Label invalid, Node success, Node... nodes) {
        Boolean isValid = false;
        if (!isNotNull(node) && rule != Rule.NOT_NULL) {
        } else {
            switch (rule) {
                case Rule.NUMERIC:
                    isValid = isNumeric(node);
                    this.setErrorMessage(Errors.IS_NUMERIC.getMessage());
                    break;
                case Rule.NOT_NULL:
                    isValid = isNotNull(node);
                    this.setErrorMessage(Errors.IS_NOT_NULL.getMessage());
                    break;
                case Rule.EMAIL:
                    isValid = isEmail(node);
                    this.setErrorMessage(Errors.IS_EMAIL.getMessage());
                    break;
                case Rule.PHONE:
                    isValid = isPhoneNumber(node);
                    this.setErrorMessage(Errors.IS_PHONE.getMessage());
                    break;
                case Rule.PASSWORD:
                    isValid = isValidPassword(node);
                    this.setErrorMessage(Errors.PASSWORD.getMessage());
                    break;
                case Rule.PASSWORD_CONFIRM:
                    if (nodes.length > 0) {
                        isValid = isPasswordConfirmed(node, nodes[0]);
                        this.setErrorMessage(Errors.PASSWORD_CONFIRMATION.getMessage());
                    }
                    break;
                case Rule.DATE:
                    isValid = this.isDate(node);
                    break;
                default:
                    break;
            }
        }
        updateNode(isValid, node, invalid, success);
        return isValid;

    }

    public void updateNode(Boolean isValid, Node node, Label invalidLabel) {
        String style = null;
        if (isValid) {
            style = "-fx-border-color: " + VALID_INPUT + " ;  \n"
                    + " -fx-border-width: 2px ;  ";
            UIHelper.setElementsVisible(Boolean.FALSE, invalidLabel);
        } else {
            style = "-fx-border-color: " + INVALID_INPUT + " ;  \n"
                    + " -fx-border-width: 2px ;  ";

            UIHelper.setElementsVisible(Boolean.TRUE, invalidLabel);
            setInvalidLabel(invalidLabel, this.getValidationMessage());
        }
        node.setStyle(style);
    }

    public void updateNode(Boolean isValid, Node node, Label invalidLabel, Node success) {
        updateNode(isValid, node, invalidLabel);
        UIHelper.setElementsVisible(isValid, success);
    }

    public Boolean isNumeric(Node node) {
        if (node instanceof TextField) {
            return ((TextField) node).getText().matches(Regex.DIGIT.getVal());
        }
        return Boolean.FALSE;
    }

    public Boolean isEmail(Node input) {
        return ((TextField) input).getText().matches(Regex.EMAIL.getVal());
    }

    public Boolean isPhoneNumber(Node input) {
        return ((TextField) input).getText().matches(Regex.PHONE_NUMBER.getVal());
    }

    public Boolean isValidPassword(Node input) {
        return ((TextField) input).getText().matches(Regex.PASSWORD_MIN_LENGTH_8.getVal());
    }

    public Boolean isDate(Node node) {
        if (node instanceof DatePicker) {
            try {
                LocalDate date = ((DatePicker) node).getValue();
                return Boolean.TRUE;
            } catch (Exception e) {
                this.setErrorMessage(Errors.IS_DATE.getMessage());
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean isPasswordConfirmed(Node confirm, Node password) {
        return ((TextField) confirm).getText().equals(((TextField) password).getText());
    }

    public Boolean isNotNull(Node node) {
        Boolean isNotNull = false;
        if (node instanceof DatePicker) {
            isNotNull = !(((DatePicker) node).getValue() == null);
            this.setErrorMessage(Errors.IS_DATE.getMessage());
        } else if (node instanceof TextField) {
            isNotNull = !((TextField) node).getText().isEmpty();
            this.setErrorMessage(Errors.IS_NOT_NULL.getMessage());
        }
        return isNotNull;
    }

    public static void setInvalidLabel(Label lbl, String error) {
        lbl.setText(error);
        lbl.setStyle("-fx-text-fill: " + INVALID_INPUT + ";");
    }

    private void setErrorMessage(String message) {
        this.error = message;
    }

    public String getValidationMessage() {
        return error;
    }
}
