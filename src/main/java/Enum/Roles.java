package Enum;

/**
 * @author kasun eranda - 12216898
 * @description This enum hold system roles   
 */
public enum Roles {
    ADMIN("administrator"),
    APPLICANT("applicant"),
    MANAGEMENT("management");
    
    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getValue() {
        return this.role;
    }
}
