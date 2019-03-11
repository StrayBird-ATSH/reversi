public class Computer implements Playable {
    private Piece piece;

    Computer() {
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public void place(Board board) {
        piece.placeOptimal(board);
    }

    @Override
    public boolean placeable(Board board) {
        return piece.placeable(board);
    }
}
