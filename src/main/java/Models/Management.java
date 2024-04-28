package Models;

import Enum.Roles;
import Interface.Csv;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@ToString(callSuper = true, includeFieldNames = true)
public class Management extends Staff implements Csv {
    private String management_lvl;
    private String branch;

    /**
     * Management model constructor
     * 
     * @param firstName
     * @param lastName
     * @param address
     * @param phone
     * @param email
     * @param username
     * @param password
     * @param management_lvl
     * @param branch
     */
    public Management(
            String firstName,
            String lastName,
            String address,
            int phone,
            String email,
            String username,
            String password,
            String management_lvl,
            String branch) {
        super(
                firstName,
                lastName,
                address,
                phone,
                email,
                username,
                password,
                Roles.MANAGEMENT.getValue());
        this.management_lvl = management_lvl;
        this.branch = branch;

    }

    @Override
    public String getCSV() {
        return super.getCSV() + ", , " + getBranch() + ", " + getManagement_lvl();
    }

    public String getManagement_lvl() {
        return management_lvl;
    }

    public void setManagement_lvl(String management_lvl) {
        this.management_lvl = management_lvl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    
    

}
