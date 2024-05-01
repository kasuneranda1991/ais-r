package Enum;

public enum Config {
    STORAGE_PATH("src/main/java/Storage/");

    private final String conf;

    Config(String conf) {
        this.conf = conf;
    }

    public String getValue() {
        return this.conf;
    }
}
