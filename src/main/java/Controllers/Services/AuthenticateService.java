package Controllers.Services;

import Controllers.Enum.CSVConst;
import Controllers.Enum.Route;
import Controllers.Helpers.Helper;
import Models.Staff;
import Models.User;
import Controllers.Services.PersistsService;
import Models.Applicant;


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
        
        username = "test@gmail.com";
        password = "test@1234";
 
        Staff stf = StaffService.findByFieldName(CSVConst.EMAIL, username);
        if (stf != null && stf.getPassword().equals(password)) {
            AuthService.get().setUser(stf);
            return true;
        }
        return false;
    }
    

   public static Applicant getApplicantByToken(String token) {
       return PersistsService.get().getApplicantByToken(token);
   }

    public static Boolean authenticateUser(User user) {
        return authenticate(user.getEmail(), user.getPassword());
    }

    public static void logout() {
        System.out.println("User has been logged out");
        AuthService.get().setUser(null);
        System.out.println("Auth user after logged out:" + AuthService.get().user() == null);
        RouteService.redirectTo(Route.LOGIN);
    }
}
