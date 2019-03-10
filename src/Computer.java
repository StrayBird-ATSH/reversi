public class Computer implements Playable {
    Color color;

    public Computer(Color color) {
        this.color = color;
    }

    public Computer() {
    }

    @Override
    public boolean place() {
        return true;
    }

    @Override
    public boolean placeable(Board board) {
        return board.placeable(color);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
