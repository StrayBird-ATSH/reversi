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
        ArrayList<Color> colorSequence;
//        In row direction
        for (int i = 0; i < size; i++) {
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (checkCanPlace(color, i, j, colorSequence)) return true;
            }
        }
//        In column direction
        for (int j = 0; j < size; j++) {
            colorSequence = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                if (checkCanPlace(color, i, j, colorSequence)) return true;
            }
        }
//        In diagonal direction
        for (int j = 0; j < size; j++) {
            int column = j;
            colorSequence = new ArrayList<>();
            for (int i = 0; i < size && column < size; i++, column++) {
                if (checkCanPlace(color, i, j, colorSequence)) return true;
            }
        }

        for (int i = 1; i < size; i++) {
            int row = i;
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size && row < size; j++, row++) {
                if (checkCanPlace(color, i, j, colorSequence)) return true;
            }
        }


//        In counter-diagonal direction

        for (int i = 0; i < size; i++) {
            int row = i;
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size && row >= 0; j++, row--) {
                if (checkCanPlace(color, i, j, colorSequence)) return true;
            }
        }

        for (int j = 1; j < size; j++) {
            int column = j;
            colorSequence = new ArrayList<>();
            for (int i = size - 1; i >= 0 && column < size; i--, column++) {
                if (checkCanPlace(color, i, j, colorSequence)) return true;
            }
        }
        return false;
    }


    private boolean checkCanPlace(Color color, int i, int j, ArrayList<Color> colorSequence) {
        Color inverseColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        Color currentColor;
        if (pieces[i][j] == null) currentColor = null;
        else currentColor = pieces[i][j].color;
        if (colorSequence.size() == 0) colorSequence.add(currentColor);
        else if (colorSequence.get(colorSequence.size() - 1) != currentColor) {
            colorSequence.add(currentColor);

            return colorSequence.size() >= 3 &&
                    colorSequence.get(colorSequence.size() - 2) == inverseColor &&
                    !(colorSequence.get(colorSequence.size() - 1) == null &&
                            colorSequence.get(colorSequence.size() - 3) == null);
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
