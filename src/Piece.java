abstract class Piece {
    Color color;

    Piece() {
    }


    abstract void computerPrompt();

    abstract void personPrompt();

    abstract Color getColor();

    abstract boolean placeable(Board board, boolean isUser);

    @Override
    abstract public String toString();

    abstract void placeOptimal(Board board);
}
