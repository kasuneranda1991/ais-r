package Services;

import java.io.IOException;

import com.cqu.aisr.App;

import Enum.Route;

public class RouteService {
    public static Route current;

    public static void redirectTo(Route route) {
        try {
            App.setRoot(route.getValue());
            current = route;
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
