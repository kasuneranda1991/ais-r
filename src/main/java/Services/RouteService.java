package Services;

import java.io.IOException;

import com.cqu.aisr.App;

import Enum.Route;

public class RouteService {
    public static Route current;

    public static void redirectTo(Route route) {
        try {
            App.setRoot(route.getValue());
            if (PersistsService.get().applicantsData().size() == 0 && route == Route.APPLICATION) {
                NotificationService.message("Not Data", "Not application data");
            }
            current = route;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void redirectToWithMessage(Route route, String message) {
        try {
            App.setRoot(route.getValue());

            NotificationService.message(message);
            current = route;
        } catch (IOException e) {
            System.out.println("EX here");
            System.out.println(e);
        }
    }
}
