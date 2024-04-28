/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kasun
 */
public enum EmploymentType {
    FULL_TIME("Full-Time"),
    PART_TIME("Part-Time"),
    VOLUNTEER("Volunteer");

    private final String emType;

    EmploymentType(String emType) {
        this.emType = emType;
    }

    public String getValue() {
        return this.emType;
    }

    public static ObservableList<String> getValues() {
        ObservableList<String> values = FXCollections.observableArrayList();
        for (EmploymentType type : EmploymentType.values()) {
            values.add(type.getValue());
        }
        return values;
    }
}
