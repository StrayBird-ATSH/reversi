public class Board {
    int size;
    Piece[][] pieces;
    int comCount = 0;
    int userCount = 0;
    Color userColor;

    public Color getUserColor() {
        return userColor;
    }

    public void setUserColor(Color userColor) {
        this.userColor = userColor;
        pieces[size / 2 - 1][size / 2 - 1] = new Piece(Color.WHITE);
        pieces[size / 2][size / 2] = new Piece(Color.WHITE);
        pieces[size / 2 - 1][size / 2] = new Piece(Color.BLACK);
        pieces[size / 2][size / 2 - 1] = new Piece(Color.BLACK);
    }

    public Board(int size) {
        this.size = size;
        pieces = new Piece[size][size];
    }
}
