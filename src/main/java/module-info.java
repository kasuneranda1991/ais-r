module com.cqu.aisr {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.mail;

    opens com.cqu.aisr to javafx.fxml;
    opens Models to javafx.base;

    exports com.cqu.aisr;

    // requires lombok;
    requires de.siegmar.fastcsv;
}
