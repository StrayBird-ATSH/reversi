class BlackPiece extends Piece {
    private final static Color color = Color.BLACK;

    @Override
    boolean placeable(Board board) {
        return board.placeable(color);
    }

    @Override
    void placeOptimal(Board board) {
        board.placeOptimal(color);
    }
}