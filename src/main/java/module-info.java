module com.cqu.aisr {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cqu.aisr to javafx.fxml;
    exports com.cqu.aisr;
}
