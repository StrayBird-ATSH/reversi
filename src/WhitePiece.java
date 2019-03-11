class WhitePiece extends Piece {
    private final static Color color = Color.WHITE;

    @Override
    boolean placeable(Board board) {
        return board.placeable(color);
    }

    @Override
    void placeOptimal(Board board) {

    }
}