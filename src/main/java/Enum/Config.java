package Enum;

public enum Config {
    STAFF_CSV_HEADING("Id" +
            ",First Name" +
            ",Last Name" +
            ",Address" +
            ",Phone" +
            ",Email" +
            ",Username" +
            ",Password" +
            ",Role" +
            ",Employment Type" +
            ",Branch" +
            ",Management Level"),
    STORAGE_PATH("src/main/java/Storage/");

    private final String conf;

    Config(String conf) {
        this.conf = conf;
    }

    public String getValue() {
        return this.conf;
    }
}
