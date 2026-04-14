package co.Daniel.Bank.view;

public enum AccountOption {
    D, S, V, E, X;

    public static AccountOption parse(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}