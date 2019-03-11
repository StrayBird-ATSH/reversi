import java.util.ArrayList;

public class Board {
    int size;
    private Piece[][] pieces;
    int comCount = 0;
    int userCount = 0;
    private Color userColor;

    Board(int size) {
        this.size = size;
        pieces = new Piece[size][size];
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


    private boolean checkDiagonalIterator(Color color, boolean isCounter) {
        ArrayList<Color> colorSequence;
        for (int i = 0; i < size; i++) {
            int k = i;
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size && k < size; j++, k++)
                if (!isCounter ? checkCanPlace(color, j, k, colorSequence) :
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

    void placeOptimal(Color color) {
        int globalMaxPoint = 0;
        int currentPoint = 0;
        boolean vacantFlag = false, inverseFlag = false, colorFlag = false;
        Color inverseColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        Color currentColor;
        int optimalI, optimalJ;
        int vacantI = 0, vacantJ = 0;
//        Row dimension
        OptimalPosition row = scanPosition(true, color);
        globalMaxPoint = row.count;
        optimalI = row.row;
        optimalJ = row.column;
        //        Column dimension
        OptimalPosition column = scanPosition(false, color);
        if (column.count > globalMaxPoint) {
            optimalI = column.row;
            optimalJ = column.column;
        }
        //        In diagonal direction


    }


    private OptimalPosition scanPosition(boolean isRow, Color color) {

        int globalMaxPoint = 0;
        int currentPoint = 0;
        boolean vacantFlag = false, inverseFlag = false, colorFlag = false;
        Color inverseColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        Color currentColor;
        int optimalI = 0, optimalJ = 0;
        int vacantI = 0, vacantJ = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isRow) {
                    if (pieces[i][j] == null) currentColor = Color.NULL;
                    else currentColor = pieces[i][j].color;
                } else if (pieces[j][i] == null) currentColor = Color.NULL;
                else currentColor = pieces[j][i].color;


                if (currentColor == Color.NULL) {
                    if (colorFlag && inverseFlag && currentPoint > globalMaxPoint) {
                        globalMaxPoint = currentPoint;
                        optimalI = i;
                        optimalJ = j;
                    }
                    vacantI = i;
                    vacantJ = j;
                    vacantFlag = true;
                    inverseFlag = false;
                    colorFlag = false;
                    currentPoint = 0;
                } else if (currentColor == color) {
                    if (inverseFlag && vacantFlag && currentPoint > globalMaxPoint) {
                        globalMaxPoint = currentPoint;
                        optimalI = vacantI;
                        optimalJ = vacantJ;
                    }
                    colorFlag = true;
                    inverseFlag = false;
                    vacantFlag = false;
                    currentPoint = 0;
                } else if (currentColor == inverseColor && colorFlag || vacantFlag) {
                    currentPoint++;
                    inverseFlag = true;
                }
            }
            vacantFlag = false;
            colorFlag = false;
            inverseFlag = false;
        }
        if (isRow) return new OptimalPosition(optimalI, optimalJ, globalMaxPoint);
        return new OptimalPosition(optimalJ, optimalI, globalMaxPoint);
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
