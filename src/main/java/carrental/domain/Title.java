package carrental.domain;

public enum Title {
    DEPOSIT("Letét"),
    PAY("Fizetés"),
    PENALTY("Büntetés"),
    DAMAGE("Kár");

    private String name;

    Title(String name) {
        this.name = name;
    }
}
