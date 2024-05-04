package Models;

import Controllers.Enum.Roles;
import Controllers.Services.PersistsService;

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

    public Staff(String firstName, String lastName, String address, int phone, String email, String username,
            String password, String role) {
        super(firstName, lastName, address, email, phone, username, password, role);
    }

    @Override
    public String getCSV() {
        return super.getCSV();
    }

    public static int totalAdmins() {
        return getStafCount(Roles.ADMIN);
    }

    public static int totalManagers() {
        return getStafCount(Roles.MANAGEMENT);
    }

    private static int getStafCount(Roles role) {
        int count = 0;
        for (User usr : PersistsService.get().staffData()) {
            if (usr.getRole().equals(role.getValue())) {
                count++;
            }
        }
        return count;
    }
}
