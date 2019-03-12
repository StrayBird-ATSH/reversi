public class Computer implements Playable {
    private Piece piece;

    Computer() {
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public void place(Board board) {
        board.placeOpt();
    }

    @Override
    public boolean placeable(Board board) {
        if (!piece.placeable(board, false)) {
            if (piece.placeable(board, true))
                System.out.print(board.computerPieceName + " player has no valid move. ");
            return false;
        }
        return true;
    }
}
