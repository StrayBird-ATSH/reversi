public class Computer implements Playable {
    private Piece piece;

    Computer() {
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * This method is called to let the player place a piece.
     *
     * @param board The current chess board object.
     */
    @Override
    public void place(Board board) {
        board.placeOpt();
    }

    /**
     * Checks whether this player can put a piece on the board.
     *
     * @param board The current chess board object.
     * @return true if the player can have valid move and false otherwise
     */
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
