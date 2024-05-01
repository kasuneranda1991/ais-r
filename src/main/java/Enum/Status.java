package Enum;

public enum Status {
    APPROVED("Approved"),
    PENDING("Pending");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getValue() {
        return this.status;
    }
}
