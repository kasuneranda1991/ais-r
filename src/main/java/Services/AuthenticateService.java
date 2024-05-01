package Services;

import Enum.Route;
import Enum.CSVConst;
import Models.Staff;

public class AuthenticateService {

    /**
     * Authenticate user
     * 
     * Authentication success : TRUE else FALSE
     * 
     * @param username
     * @param password
     * @return boolean
     */
    public static Boolean authenticate(String username, String password) {
        username = "admin@gmail.com";
        password = "admin";
        Staff stf = StaffService.findByFieldName(CSVConst.EMAIL, username);
        if (stf != null && stf.getPassword().equals(password)) {
            AuthService.get().setUser(stf);
            return true;
        }
        return false;
    }

    public static void logout() {
        AuthService.get().setUser(null);
        RouteService.redirectTo(Route.LOGIN);
    }
}
