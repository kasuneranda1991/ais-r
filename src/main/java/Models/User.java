package Models;

import Controllers.Enum.Roles;
import Controllers.Interface.Csv;

/**
 * @author Kasun Eranda - 12216898
 * @description This class represent data model for a system staff member
 */
// @Getter
// @Setter
// @ToString
public class User extends Model implements Csv {

    private String firstName;
    private String lastName;
    private String address;
    private int phone;
    private String email;
    private String username;
    private String password;
    private String role;
    private String branch;

    /**
     * Default constructor
     */
    public User() {
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
    public User(
            String firstName,
            String lastName,
            String address,
            String email,
            int phone,
            String username,
            String password,
            String role) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String getCSV() {

        return getId() + "," +
                getFirstName() + "," +
                getLastName() + "," +
                getAddress() + "," +
                Integer.toString(getPhone()) + "," +
                getEmail() + "," +
                getUsername() + "," +
                getPassword() + "," +
                getRole();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    private Boolean isRole(Roles role) {
        return getRole().trim().equals((role.getValue()).trim());
    }

    public Boolean isAdmin() {
        return isRole(Roles.ADMIN);
    }

    public Boolean isManager() {
        return isRole(Roles.MANAGEMENT);
    }

    public void setRole(String role) {
        this.role = role;
    }
}
