package Controllers.Enum;

public enum Config {
    STORAGE_PATH("src/main/java/Storage/"),
    SECRET_KEY("ZAK8d0xd9W0fG9dmxCTiOw=="),
    INACTIVITY("600000"),
    ENCRYPT_DATA("FALSE"), // TRUE | FALSE,
    DB_PASSWORD("root");

    private final String conf;

    Config(String conf) {
        this.conf = conf;
    }

    public String getValue() {
        return this.conf;
    }

    public Boolean asBoolean() {
        return Boolean.parseBoolean(this.conf);
    }
}