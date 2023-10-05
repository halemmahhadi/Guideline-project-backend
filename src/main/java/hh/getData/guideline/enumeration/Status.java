package hh.getData.guideline.enumeration;

public enum Status {
    ACTIVE("true"),
    INACTIVE("false");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
