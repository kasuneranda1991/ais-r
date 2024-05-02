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

    private Boolean validateRule(Node node, Rule rule, Node... nodes) {
        Boolean isValid = false;
        if (!isNotNull(node) && rule != Rule.NOT_NULL) {
            return false;
        }

        switch (rule) {
            case NUMERIC:
                isValid = isNumeric(node);
                setErrorMessage(Errors.IS_NUMERIC.getMessage());
                break;
            case NOT_NULL:
                isValid = isNotNull(node);
                setErrorMessage(Errors.IS_NOT_NULL.getMessage());
                break;
            case EMAIL:
                isValid = isEmail(node);
                setErrorMessage(Errors.IS_EMAIL.getMessage());
                break;
            case PHONE:
                isValid = isPhoneNumber(node);
                setErrorMessage(Errors.IS_PHONE.getMessage());
                break;
            case PASSWORD:
                isValid = isValidPassword(node);
                setErrorMessage(Errors.PASSWORD.getMessage());
                break;
            case PASSWORD_CONFIRM:
                if (nodes.length > 0) {
                    isValid = isPasswordConfirmed(node, nodes[0]);
                    setErrorMessage(Errors.PASSWORD_CONFIRMATION.getMessage());
                }
                break;
            case DATE:
                isValid = isDate(node);
                break;
            default:
                break;
        }

        return isValid;
    }

    public Boolean validate(Node node, Rule rule, Label invalid) {
        Boolean isValid = validateRule(node, rule);
        updateNode(isValid, node, invalid);
        return isValid;

    }

    public Boolean validate(Node node, Rule rule, Label invalid, Node success, Node... nodes) {
        Boolean isValid = validateRule(node, rule, nodes);
        updateNode(isValid, node, invalid, success);
        return isValid;

    }

    public void updateNode(Boolean isValid, Node node, Label invalidLabel) {
        String style = isValid ? getBorderStyle(VALID_INPUT) : getBorderStyle(INVALID_INPUT);
        node.setStyle(style);
        UIHelper.setElementsVisible(!isValid, invalidLabel);
        setInvalidLabel(invalidLabel, getValidationMessage());
    }

    private String getBorderStyle(String color) {
        return "-fx-border-color: " + color + " ;  \n" + " -fx-border-width: 2px ;  ";
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
