class BlackPiece extends Piece {
    private final static Color color = Color.BLACK;

    @Override
    void personPrompt() {
        System.out.println("Enter move for X (RowCol):");
    }

    @Override
    void computerPrompt() {
        System.out.println("Computer places X at:");
    }

    @Override
    boolean placeable(Board board) {
        return board.placeable(color);
    }

    @Override
    void placeOptimal(Board board) {

    }

    @Override
    public String toString() {
        return "X";
    }
}