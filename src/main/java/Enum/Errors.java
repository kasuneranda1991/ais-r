package Enum;

/**
 * This enum represent used validation messages
 * @author kasun
 */
public enum Errors {
    
    IS_NUMERIC("Please enter a numeric value"),
    IS_NOT_NULL("Please enter a value"),
    IS_EMAIL("Please enter a valid email address"),
    IS_PHONE("Please enter a valid phone number"),
    PASSWORD("Please enter a valid password min length 8 characters"),
    PASSWORD_CONFIRMATION("Passwords does not match");

    private final String error;

    /**
     * Construct enum error
     * @param String error
     */
    Errors(String error) {
        this.error = error;
    }

    /**
     * Return error message
     * @return String error
     */
    public String getMessage() {
        return error;
    }
}
