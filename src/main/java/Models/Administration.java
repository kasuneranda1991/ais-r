package Models;

import Enum.Roles;
import Interface.Csv;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;

/**
 *
 * @author kasun eranda - 12216898
 */
//@Getter
//@Setter
//@ToString(callSuper = true, includeFieldNames = true)
public class Administration extends Staff implements Csv {

    private String employment_type;

    public Administration(
            String firstName,
            String lastName,
            String address,
            int phone,
            String email,
            String username,
            String password,
            String employment_type) {
        super(
                firstName,
                lastName,
                address,
                phone,
                email,
                username,
                password,
                Roles.ADMIN.getValue());
        this.employment_type = employment_type;
    }

    @Override
    public String getCSV() {
        return super.getCSV() + "," + getEmployment_type();
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }
    
}
