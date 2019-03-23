abstract class Piece {
    Color color;

    Piece() {
    }

    abstract void personPrompt();

    abstract boolean placeable(Board board, boolean isUser);

    @Override
    abstract public String toString();
}
