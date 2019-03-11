public class OptimalAttributes {
    int row;
    int column;
    int count;
    int globalMaxPoint = 0;
    int currentPoint = 0;
    boolean vacantFlag = false, inverseFlag = false, colorFlag = false;
    Color inverseColor;
    Color currentColor;
    Color color;
    int optimalI = 0, optimalJ = 0;
    int vacantI = 0, vacantJ = 0;

    public OptimalAttributes() {
    }

    public OptimalAttributes(int row, int column, int count) {
        this.row = row;
        this.column = column;
        this.count = count;
    }
}
