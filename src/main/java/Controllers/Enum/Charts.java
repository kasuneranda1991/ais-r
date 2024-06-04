package Controllers.Enum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Charts {
    BAR("Bar Chart"),
    PIE("Pie Chart")
    ;

    private final String chart;

    Charts(String chart) {
        this.chart = chart;
    }

    public String getValue() {
        return this.chart;
    }

    public static ObservableList<String> getValues() {
        ObservableList<String> values = FXCollections.observableArrayList();
        for (Charts type : Charts.values()) {
            values.add(type.getValue());
        }
        return values;
    }
}
