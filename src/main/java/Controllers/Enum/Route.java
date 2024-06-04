package Controllers.Enum;

public enum Route {
    DASHBOARD("Dashboard"),
    REGISTRATION("Registration"),
    APPLICATION("Application"),
    APPLICATION_CREATE("ApplicationCreate"),
    VACANCIES("Vacancies"),
    REPORT("Report"),
    STAFF("Staff"),
    CHART("Charts"),
    LOGOUT("Login"),
    EDITAPPLICANT("editApplicant"),
    LOGIN("Login");

    private final String route;

    Route(String route) {
        this.route = route;
    }

    public String getValue() {
        return this.route;
    }
}
