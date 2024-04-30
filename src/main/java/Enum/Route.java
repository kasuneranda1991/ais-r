package Enum;

public enum Route {
    DASHBOARD("Dashboard"),
    REGISTRATION("Registration"),
    APPLICATION("Application"),
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
