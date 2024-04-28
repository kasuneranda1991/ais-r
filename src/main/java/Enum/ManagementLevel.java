package Enum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum ManagementLevel{
    SENIOR_MNGR("Senior_Manager"),
    SUPERVISOR("Supervisor"),
    MID_LVL_MNGR("Mid_Level_Manage");
    
    

    private final String lvl;

    ManagementLevel(String lvl) {
        this.lvl = lvl;
    }

    public String getValue() {
        return this.lvl;
    }
    
     public static ObservableList<String> getValues() {
        ObservableList<String> values = FXCollections.observableArrayList();
        for (ManagementLevel type : ManagementLevel.values()) {
            values.add(type.getValue());
        }
        return values;
    }
}
