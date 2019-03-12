class BlackPiece extends Piece {

    @Override
    void personPrompt() {
        System.out.println("Enter move for X (RowCol):");
    }

    BlackPiece() {
        color = Color.BLACK;
    }

    @Override
    Color getColor() {
        return color;
    }

    @Override
    void computerPrompt() {
        System.out.println("Computer places X at:");
    }

    @Override
    boolean placeable(Board board, boolean isUser) {
        return board.checkCanPlace(isUser);
    }

    @Override
    void placeOptimal(Board board) {

    }

    @Override
    public String toString() {
        return "X ";
    }
}