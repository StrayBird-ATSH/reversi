import java.util.ArrayList;

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
        pieces[size / 2 - 1][size / 2 - 1] = new WhitePiece();
        pieces[size / 2][size / 2] = new WhitePiece();
        pieces[size / 2 - 1][size / 2] = new BlackPiece();
        pieces[size / 2][size / 2 - 1] = new BlackPiece();
    }

    public Board(int size) {
        this.size = size;
        pieces = new Piece[size][size];
    }

    boolean placeable(Color color) {
        Color inverseColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        ArrayList<Color> colorSequence = new ArrayList<>();
        Color currentColor = null;
//        In row direction
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pieces[i][j] == null) currentColor = null;
                else currentColor = pieces[i][j].color;
                if (colorSequence.size() == 0) colorSequence.add(currentColor);
                else if (colorSequence.get(colorSequence.size() - 1) != currentColor) {
                    colorSequence.add(currentColor);
                    if (colorSequence.size() >= 3 &&
                            colorSequence.get(colorSequence.size() - 2) == inverseColor &&
                            !(colorSequence.get(colorSequence.size() - 1) == null &&
                                    colorSequence.get(colorSequence.size() - 3) == null)) return true;
                }
            }
            colorSequence = new ArrayList<>();
        }
//        In column direction
//        In diagonal direction
//        In counter-diagonal direction


        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
