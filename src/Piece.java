abstract class Piece {
    Color color;

    Piece() {
    }

    abstract boolean placeable(Board board);

    abstract void placeOptimal(Board board);
}
