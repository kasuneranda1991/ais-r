package Controllers.Enum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Branch {
    MEL("Melbourne"),
    BNE("Brisbane"),
    ADL("Adelaide"),
    SYD("Sydney");

    private final String branch;

    Branch(String branch) {
        this.branch = branch;
    }

    public String getValue() {
        return this.branch;
    }

    public static ObservableList<String> getValues() {
        ObservableList<String> values = FXCollections.observableArrayList();
        for (Branch type : Branch.values()) {
            values.add(type.getValue());
        }
        return values;
    }

    public static Branch getEnum(String branch) {
        for (Branch type : Branch.values()) {
            if (type.getValue().equals(branch)) {
                return type;
            }
        }
        // Default return value if no match is found
        return null;
    }

}
