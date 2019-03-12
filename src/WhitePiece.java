class WhitePiece extends Piece {
    WhitePiece() {
        color = Color.WHITE;
    }

    @Override
    boolean placeable(Board board, boolean isUser) {
        return board.checkCanPlace(isUser);
    }

    @Override
    Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "O ";
    }

    @Override
    void personPrompt() {
        System.out.println("Enter move for O (RowCol):");
    }

    @Override
    void computerPrompt() {
        System.out.println("Computer places O at:");
    }

    @Override
    void placeOptimal(Board board) {
    }
}