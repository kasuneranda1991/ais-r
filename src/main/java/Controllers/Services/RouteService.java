package Controllers.Services;

import java.io.IOException;

import com.cqu.aisr.App;

import Controllers.Enum.Route;

public class RouteService {
    public static Route current;

    public static void redirectTo(Route route) {
        try {
            App.setRoot(route.getValue());
            if (PersistsService.get().applicantsData().size() == 0 && route == Route.APPLICATION) {
                NotificationService.message("Not Data", "Not application data");
            }
            RouteService.current = route;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void redirectToWithMessage(Route route, String message) {
        try {
            System.out.println("----------Route : " + route.getValue());
            System.out.println("----------Message : " + message);
            App.setRoot(route.getValue());
            System.out.println("----------Before Notification : ");
            NotificationService.message(message);
            System.out.println("----------After Notification : ");
            RouteService.current = route;
            System.out.println("----------Current route : " + current);
        } catch (IOException e) {
            System.out.println("EX here");
            System.out.println(e.getStackTrace());
        }
    }
}
