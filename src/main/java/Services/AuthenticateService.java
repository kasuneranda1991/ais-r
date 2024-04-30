package Services;

import Enum.Route;
import Enum.StaffEnum;
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
        username = "admin@admin.com";
        password = "123456789";
        Staff stf = StaffService.findByFieldName(StaffEnum.EMAIL, username);
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
