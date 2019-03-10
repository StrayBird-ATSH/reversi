abstract public class Piece {
    Color color;

    public Piece() {
    }

    public Piece(Color color) {
        this.color = color;
    }

    abstract void placeable();
}
