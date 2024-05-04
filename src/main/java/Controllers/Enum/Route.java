package Controllers.Enum;

public enum Route {
    DASHBOARD("Dashboard"),
    REGISTRATION("Registration"),
    APPLICATION("Application"),
    APPLICATION_CREATE("ApplicationCreate"),
    VACANCIES("Vacancies"),
    REPORT("Report"),
    STAFF("Staff"),
    LOGOUT("Login"),
    LOGIN("Login");

    private final String route;

    Route(String route) {
        this.route = route;
    }

    public String getValue() {
        return this.route;
    }
}
