package Services;

import javafx.scene.control.Alert;

public class NotificationService {

    private static final int ALERT_WIDTH = 600;
    private static final String DEFAULT_TITLE = "Information";

    public static void message(String message) {
        showMessage(DEFAULT_TITLE, message);
    }

    public static void message(String title, String message) {
        showMessage(title, message);
    }

    private static void showMessage(String title, String message) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setWidth(ALERT_WIDTH);
        informationAlert.setTitle(title);
        informationAlert.setContentText(message);
        informationAlert.showAndWait();
    }
}
