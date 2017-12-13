package service1.domain;

public enum Type {
    DATA, JSON, XML;

    public static Type getDefault() {
        return JSON;
    }

    public String toString() {
        switch (this) {
            case DATA:
                return "DATA";
            case JSON:
                return "JSON";
            case XML:
                return "XML";
            default:
                return "ERROR";
        }
    }
}
