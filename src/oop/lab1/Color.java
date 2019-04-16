package oop.lab1;

public enum Color {
    BLACK, WHITE, BLANK, NA;

    public Color opposite() {
        switch (this) {
            case BLACK:
                return WHITE;
            case WHITE:
                return BLACK;
            default:
                throw new RuntimeException("invalid");
        }
    }

}
