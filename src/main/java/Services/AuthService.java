package Services;

import Models.Staff;

public class AuthService {
    private Staff auth;
    private static AuthService instance = null;
    
    private AuthService() {
    }
    
    public static AuthService get(){
        if(instance == null){
            instance = new AuthService();
        }
        return instance;
    }
    
    public Staff user() {
        return auth;
    }
    
    public void setUser(Staff auth){
        this.auth = auth;
    }
}
