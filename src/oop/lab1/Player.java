package oop.lab1;

//人类玩家
abstract public class Player {

    private Color color;

    public Player(Color color) {
        this.color = color;
    }

    public abstract void action(Session session);

    public Color getColor() {
        return color;
    }

}
