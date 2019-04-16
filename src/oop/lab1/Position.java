package oop.lab1;

import com.google.common.collect.Lists;

import java.util.Collection;

public class Position {

    static Collection<Position> allDirections = Lists.newArrayList(
            Position.of(1, 0), Position.of(-1, 0),
            Position.of(0, 1), Position.of(0, -1),
            Position.of(1, 1), Position.of(-1, -1),
            Position.of(1, -1), Position.of(-1, 1)
    );

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static public Position of(int x, int y) {
        return new Position(x, y);
    }

    //沿指定方向的下一个格子
    public Position nextAlongDir(Position direction) {
        return Position.of(x + direction.x, y + direction.y);
    }

    public boolean equals(Object p) {
        Position other = ((Position) p);
        return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        return (new Integer(x * 100 + y)).hashCode();
    }

    @Override
    public String toString() {
        return "x=" + x + "&& y=" + y;
    }

}
