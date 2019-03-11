class WhitePiece extends Piece {
    private final static Color color = Color.WHITE;

    @Override
    boolean placeable(Board board) {
        return board.placeable(color);
    }

    @Override
    public String toString() {
        return "O";
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