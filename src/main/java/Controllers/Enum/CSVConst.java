package Controllers.Enum;

import java.util.Arrays;

public enum CSVConst {
    ID("id"),
    FNAME("First Name"),
    LNAME("Last Name"),
    ADDRESS("Address"),
    PHONE("Phone"),
    EMAIL("Email"),
    INTW_DATE("Interview Date"),
    EDU("Education"),
    CREATED_AT("Created at"),
    CREATED_BY("Created by"),
    CREATED_BRANCH("Created branch"),
    STATUS("Status"),
    USERNAME("Username"),
    PASSWORD("Password"),
    ROLE("Role"),
    EM_TYPE("Employment Type"),
    BRANCH("Branch"),
    MGMT_LVL("Management Level"),
    
    RECRUITS_CSV_HEADING(ID.getValue() +
            "," + FNAME.getValue() +
            "," + LNAME.getValue() +
            "," + ADDRESS.getValue() +
            "," + PHONE.getValue() +
            "," + EMAIL.getValue() +
            "," + USERNAME.getValue() +
            "," + PASSWORD.getValue() +
            "," + ROLE.getValue() +
            "," + EDU.getValue() +
            "," + INTW_DATE.getValue() +
            "," + CREATED_BRANCH.getValue() +
            "," + CREATED_BY.getValue() +
            "," + CREATED_AT.getValue() +
            "," + STATUS.getValue()),
            
    STAFF_CSV_HEADING(ID.getValue() +
            "," + FNAME.getValue() +
            "," + LNAME.getValue() +
            "," + ADDRESS.getValue() +
            "," + PHONE.getValue() +
            "," + EMAIL.getValue() +
            "," + USERNAME.getValue() +
            "," + PASSWORD.getValue() +
            "," + ROLE.getValue() +
            "," + EM_TYPE.getValue() +
            "," + BRANCH.getValue() +
            "," + MGMT_LVL.getValue());

    private final String conf;
    private static CSVConst heading;

    CSVConst(String conf) {
        this.conf = conf;
    }

    public String getValue() {
        return this.conf;
    }

    public static int getFieldIndexForHeading(CSVConst field, CSVConst heading) {
        CSVConst.heading = heading;
        return getIndex(field);
    }

    public static int getFieldIndex(CSVConst field) {
        heading = CSVConst.STAFF_CSV_HEADING;
        return getIndex(field);
    }

    private static int getIndex(CSVConst field) {
        return Arrays.asList((heading.getValue()).split(",")).indexOf(field.getValue());
    }

}
