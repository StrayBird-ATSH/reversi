public class Person implements Playable {

    private Piece piece;

    Person() {
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public boolean placeable(Board board) {
        return piece.placeable(board);
    }

    @Override
    public void place() {
    }
}
