package Controllers.Enum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Department {
    SWD("Software Development"),
    ARS("Aerospace"),
    MEC("Mecanical"),
    ELEC("Electronic");

    private final String dept;

    Department(String dept) {
        this.dept = dept;
    }

    public String getValue() {
        return this.dept;
    }

    public static ObservableList<String> getValues() {
        ObservableList<String> values = FXCollections.observableArrayList();
        for (Department type : Department.values()) {
            values.add(type.getValue());
        }
        return values;
    }
}
