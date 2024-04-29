package Services;

import java.io.IOException;

import com.cqu.aisr.App;

public class RouteService {

    public static void redirectTo(String page) {
        try {
            App.setRoot(page);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
