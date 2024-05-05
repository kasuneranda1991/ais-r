package Controllers.Helpers;

import java.util.UUID;

import javafx.scene.control.TextField;

/**
 * @author Kasun Eranda - 12216898
 * @description Helper class contains methods to assist in reusable common
 *              functions.
 */
public class Helper {

    /**
     * Generates a unique identifier (UID).
     * 
     * @return Sting A unique String identifier.
     */
    public static String generateUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Get text value from the input text fields
     * 
     * @param input field
     * @return String
     */
    public static String getText(TextField input) {
        return input.getText();
    }

    /**
     * Get Int value from the input text fields
     * 
     * @param input
     * @return Integer
     */
    public static Integer getInt(TextField input) {
        return Integer.parseInt(input.getText());
    }
}
