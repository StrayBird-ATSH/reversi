class BlackPiece extends Piece {

    @Override
    void personPrompt() {
        System.out.println("Enter move for X (RowCol):");
    }

    BlackPiece() {
        color = Color.BLACK;
    }

    @Override
    boolean placeable(Board board, boolean isUser) {
        return board.checkCanPlace(isUser);
    }

    @Override
    public String toString() {
        return "X ";
    }
}