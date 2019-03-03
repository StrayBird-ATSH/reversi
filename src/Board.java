public class Board {
    int size = 4;
    Piece[][] pieces;
    int comCount = 0;
    int userCount = 0;

    public Board(int size) {
        this.size = size;
        pieces = new Piece[size][size];

    }
}
