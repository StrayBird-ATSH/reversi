class WhitePiece extends Piece {
    WhitePiece() {
        color = Color.WHITE;
    }

    /**
     * Checks whether this player can put a piece on the board.
     *
     * @param board  The current chess board object.
     * @param isUser if the caller player is user
     * @return true if the player can have valid move and false otherwise
     */
    @Override
    boolean placeable(Board board, boolean isUser) {
        return board.checkCanPlace(isUser);
    }

    /**
     * Returns a string representation of the object.
     * This is used to print the entire chess board.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "O ";
    }

    /**
     * Display the prompt line on the screen.
     */
    @Override
    void personPrompt() {
        System.out.println("Enter move for O (RowCol):");
    }
}