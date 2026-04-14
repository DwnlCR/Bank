package co.Daniel.Bank.view;

public enum MainOption {
    C, E, S;

    public static MainOption parse(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}