package Enum;

import java.util.Arrays;

public enum StaffEnum {
    ID("id"),
    FNAME("First Name"),
    LNAME("Last Name"),
    ADDRESS("Address"),
    PHONE("Phone"),
    EMAIL("Email"),
    USERNAME("Username"),
    PASSWORD("Password"),
    ROLE("Role"),
    EM_TYPE("Employment Type"),
    BRANCH("Branch"),
    MGMT_LVL("Management Level"),
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

    StaffEnum(String conf) {
        this.conf = conf;
    }

    public String getValue() {
        return this.conf;
    }

    public static int getFieldIndex(StaffEnum field) {
        return Arrays.asList((StaffEnum.STAFF_CSV_HEADING.getValue()).split(",")
            ).indexOf(field.getValue());
    }
}
