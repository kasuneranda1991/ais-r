package Enum;

/**
 * This enum represents used regular expressions.
 * @author  Kasun Eranda - 12216898
 */
public enum Regex {
    /**
     * Regular expression for matching digits.
     */
    DIGIT("\\d+"),
    /**
     * Regular expression for validating passwords with a minimum length of 8 characters.
     */
    PASSWORD_MIN_LENGTH_8(".{8,}"),
    /**
     * Regular expression for matching email addresses. It follows the standard
     * email format rules.
     */
    EMAIL("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"),
    /**
     * Regular expression for matching a 10-digit phone number.
     */
    PHONE_NUMBER("\\d{10}");

    private final String regex;

    /**
     * Constructs a Regex enum with the provided regular expression.
     *
     * @param String regex The regular expression string.
     */
    Regex(String regex) {
        this.regex = regex;
    }

    /**
     * Gets the regular expression string.
     *
     * @return The regular expression string.
     */
    public String getVal() {
        return regex;
    }
}
