package Enum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum EduQualification {
    DIP("Diploma"),
    Bachelor("Bachelor"),
    PHD("PhD");

    private final String edu;

    EduQualification(String edu) {
        this.edu = edu;
    }

    public String getValue() {
        return this.edu;
    }
    
    public static ObservableList<String> getValues() {
        ObservableList<String> values = FXCollections.observableArrayList();
        for (EduQualification edu : EduQualification.values()) {
            values.add(edu.getValue());
        }
        return values;
    }
}
