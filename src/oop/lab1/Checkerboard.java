package oop.lab1;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public class Checkerboard {

    private int max_x;
    private int max_y;
    private HashMap<Position, Color> chesses = new HashMap<Position, Color>();

    /*
     * 初始化棋盘
     */
    public Checkerboard(int i, int j) {

        assert (i % 2 == 0);
        assert (j % 2 == 0);

        this.max_x = i;
        this.max_y = j;

        for (int x = 0; x < max_x; x++) {
            for (int y = 0; y < max_y; y++) {
                chesses.put(Position.of(x, y), Color.BLANK);
            }
        }

        chesses.put(Position.of(max_x / 2 - 1, max_y / 2 - 1), Color.WHITE);
        chesses.put(Position.of(max_x / 2, max_y / 2), Color.WHITE);
        chesses.put(Position.of(max_x / 2 - 1, max_y / 2), Color.BLACK);
        chesses.put(Position.of(max_x / 2, max_y / 2 - 1), Color.BLACK);

    }

    public Color get(Position position) {
        return chesses.get(position);
    }

    // 获取落在指定位置上某种颜色的棋子可翻转的对方棋子
    private Stream<Position> getTurnOverPositions(Position start, Color color) {

        return Position.allDirections.stream().flatMap(dir -> {
            return getTurnOverPositions(color, start, dir);
        });
    }

    // 获取指定方向上所有可被翻转的棋子
    private Stream<Position> getTurnOverPositions(Color color, Position start, Position dir) {

        ArrayList<Position> result = new ArrayList<Position>();
        for (Position i : iterable(start, dir)) {
            if (get(i) == color)
                result.add(i);
            else
                return result.stream();
        }
        return new ArrayList<Position>().stream();
    }

    // 获取从一个位置，沿某一方向的遇到的所有的棋子
    private Iterable<Position> iterable(Position start, Position dir) {
        return new BoardIterable(start, dir);
    }

    public int getMax_x() {
        return max_x;
    }

    public int getMax_y() {
        return max_y;
    }

    public Stream<Position> allPositionsOfColor(Color color) {
        return chesses.keySet().stream().filter(e -> {
            return chesses.get(e) == color;
        });
    }

    // 在指定位置放一个指定颜色的棋子
    public void put(Position position, Color color) {
        assert (get(position) == Color.BLANK);
        Stream<Position> positions = getTurnOverPositions(position, color.opposite());
        assert (positions.count() > 0);

        chesses.put(position, color);
        positions.forEach(e -> {
            // turn over
            chesses.put(e, chesses.get(e).opposite());
        });
    }

    /* 计算棋盘上最佳落子位置 */
    public Optional<Position> getAttactingPosition(Color color) {
        return allPositionsOfColor(Color.BLANK)
                // 每个空白格落子后可翻转对方棋子的数量
                .map(p -> {
                    return new SimpleEntry<Position, Long>(
                            p, getTurnOverPositions(p, color.opposite()).count());
                })
                // 获取翻转数最大的位置
                .max((e1, e2) -> {
                    return e1.getValue().compareTo(e2.getValue());
                })
                // 仅返回位置
                .map(e -> {
                    return e.getKey();
                });
    }

    /*
     * 从指定一点开始，沿指定方向的所有棋子的迭代器
     */
    private final class BoardIterable implements Iterable<Position> {

        private final Position start;
        private final Position dir;

        private BoardIterable(Position start, Position dir) {
            this.start = start;
            this.dir = dir;
        }

        @Override
        public Iterator<Position> iterator() {
            return new Iterator<Position>() {
                Position curPosition = start;

                @Override
                public boolean hasNext() {
                    // 沿这个方向下面还有棋子
                    Color next = get(curPosition.nextAlongDir(dir));
                    return next == Color.BLACK || next == Color.WHITE;
                }

                @Override
                public Position next() {
                    Position n = curPosition.nextAlongDir(dir);
                    curPosition = n;
                    return n;
                }
            };
        }
    }

}
