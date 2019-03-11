abstract class Piece {
    Color color;

    Piece() {
    }


    abstract void computerPrompt();

    abstract void personPrompt();

    abstract boolean placeable(Board board);

    @Override
    abstract public String toString();

    abstract void placeOptimal(Board board);
}
