package utils;

public enum RequestType {


    AUTH("auth"), USERS("users");

    private String type;

    RequestType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
