package carrental.domain;

public enum Role {
    ADMIN("admin"),
    CUSTOMER("vásárló");

    private final String text;

    private Role(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
