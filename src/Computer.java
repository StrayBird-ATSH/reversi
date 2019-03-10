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

    public void setColor(Color color) {
        this.color = color;
    }
}
