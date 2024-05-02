package Services;

import javafx.scene.control.Alert;

public class NotificationService {

    public static void message(String title, String message) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setWidth(600);
        informationAlert.setTitle(title);
        informationAlert.setContentText(message);
        informationAlert.showAndWait();
    }
    
    public static void message(String message) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setWidth(600);
        informationAlert.setTitle("Information");
        informationAlert.setContentText(message);
        informationAlert.showAndWait();
    }
}
