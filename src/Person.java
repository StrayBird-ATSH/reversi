public class Person implements Playable {

    Color color;

    public Person() {
    }

    public Person(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean place() {
        return true;
    }
}
