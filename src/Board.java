import java.util.ArrayList;

public class Board {
    int size;
    private Piece[][] pieces;
    int comCount = 0;
    int userCount = 0;
    private Color userColor;
    private OptimalAttributes place;

    Board(int size) {
        this.size = size;
        pieces = new Piece[size][size];
        place = new OptimalAttributes();
    }

    Color getUserColor() {
        return userColor;
    }

    void setUserColor(Color userColor) {
        this.userColor = userColor;
        pieces[size / 2 - 1][size / 2 - 1] = new WhitePiece();
        pieces[size / 2][size / 2] = new WhitePiece();
        pieces[size / 2 - 1][size / 2] = new BlackPiece();
        pieces[size / 2][size / 2 - 1] = new BlackPiece();
    }

    /*     PLACE-ABLE CHECKER ----START-----     */

    boolean placeable(Color color) {
        ArrayList<Color> colorSequence;
//        In row direction
        if (checkIterator(color, true)) return true;
//        In column direction
        if (checkIterator(color, false)) return true;
//        In diagonal direction
        if (checkDiagonalIterator(color, false)) return true;
        if (checkDiagonalIterator(color, true)) return true;
//        In counter-diagonal direction
        for (int i = 0; i < size; i++) {
            int row = i;
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size && row >= 0; j++, row--)
                if (checkCanPlace(color, row, j, colorSequence)) return true;
        }
        for (int j = 1; j < size; j++) {
            int column = j;
            colorSequence = new ArrayList<>();
            for (int i = size - 1; i >= 0 && column < size; i--, column++)
                if (checkCanPlace(color, i, column, colorSequence)) return true;
        }
        return false;
    }

    private boolean checkIterator(Color color, boolean isRow) {
        ArrayList<Color> colorSequence;
        for (int i = 0; i < size; i++) {
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size; j++)
                if (isRow ? checkCanPlace(color, i, j, colorSequence) :
                        checkCanPlace(color, j, i, colorSequence))
                    return true;
        }
        return false;
    }


    private boolean checkDiagonalIterator(Color color, boolean isLower) {
        ArrayList<Color> colorSequence;
        for (int i = 0; i < size; i++) {
            int k = i;
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size && k < size; j++, k++)
                if (!isLower ? checkCanPlace(color, j, k, colorSequence) :
                        checkCanPlace(color, k, j, colorSequence))
                    return true;
        }
        return false;
    }

    private boolean checkCanPlace(Color color, int i, int j, ArrayList<Color> colorSequence) {
        Color inverseColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        Color currentColor;
        if (pieces[i][j] == null) currentColor = Color.NULL;
        else currentColor = pieces[i][j].color;
        if (colorSequence.size() == 0) colorSequence.add(currentColor);
        else if (colorSequence.get(colorSequence.size() - 1) != currentColor) {
            colorSequence.add(currentColor);
            return colorSequence.size() >= 3 &&
                    colorSequence.get(colorSequence.size() - 2) == inverseColor &&
                    !(colorSequence.get(colorSequence.size() - 1) == Color.NULL &&
                            colorSequence.get(colorSequence.size() - 3) == Color.NULL);
        }
        return false;
    }


    /*     PLACE-ABLE CHECKER ----END-----     */

    void placeOptimal(Color color) {
        place.color = color;
        place.inverseColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
//        Row dimension
        scanPosition(true);
        //        Column dimension
        scanPosition(false);
        //        In diagonal direction


    }


    private void scanPosition(boolean isRow) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isRow) {
                    if (pieces[i][j] == null) place.currentColor = Color.NULL;
                    else place.currentColor = pieces[i][j].color;
                } else if (pieces[j][i] == null) place.currentColor = Color.NULL;
                else place.currentColor = pieces[j][i].color;


                if (place.currentColor == Color.NULL) {
                    if (place.colorFlag && place.inverseFlag && place.currentPoint >
                            place.globalMaxPoint) {
                        place.globalMaxPoint = place.currentPoint;
                        if (isRow) {
                            place.optimalI = i;
                            place.optimalJ = j;
                        } else {
                            place.optimalJ = i;
                            place.optimalI = j;
                        }
                    }
                    place.vacantI = i;
                    place.vacantJ = j;
                    place.vacantFlag = true;
                    place.inverseFlag = false;
                    place.colorFlag = false;
                    place.currentPoint = 0;
                } else if (place.currentColor == place.color) {
                    if (place.inverseFlag && place.vacantFlag && place.currentPoint >
                            place.globalMaxPoint) {
                        place.globalMaxPoint = place.currentPoint;
                        if (isRow) {
                            place.optimalI = place.vacantI;
                            place.optimalJ = place.vacantJ;
                        } else {
                            place.optimalI = place.vacantI;
                            place.optimalJ = place.vacantJ;
                        }
                    }
                    place.colorFlag = true;
                    place.inverseFlag = false;
                    place.vacantFlag = false;
                    place.currentPoint = 0;
                } else if (place.currentColor == place.inverseColor && place.colorFlag ||
                        place.vacantFlag) {
                    place.currentPoint++;
                    place.inverseFlag = true;
                }
            }
            place.vacantFlag = false;
            place.colorFlag = false;
            place.inverseFlag = false;
        }
    }


    void finish() {
//        todo
    }

    @Override
    public String toString() {
//        todo
        return super.toString();
    }
}
