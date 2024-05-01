package Models;

/**
 * @author Kasun Eranda - 12216898
 * @description This class represent data model for a system staff member
 */
// @Getter
// @Setter
// @ToString
public class Staff extends User {

    /**
     * Default constructor
     */
    public Staff() {
    }

    /**
     * Parameterized constructor
     * 
     * @param firstName
     * @param lastName
     * @param address
     * @param phone
     * @param email
     * @param username
     * @param password
     * @param role
     */

    public Staff(String firstName, String lastName, String address, int phone, String email, String username, String password, String role) {
        super(firstName, lastName, address, email, phone, username, password, role);
    }

    @Override
    public String getCSV() {
        return super.getCSV();
    }
}
