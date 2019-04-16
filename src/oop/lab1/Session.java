package oop.lab1;

import java.util.ArrayList;
import java.util.Date;

public class Session {

    private Date startTime;
    private Date finishTime;

    private Checkerboard board;

    private int curPlayer = 0;

    private ArrayList<Player> players = new ArrayList<Player>();

    public Session(int maxx, int maxy, Player player1, Player player2) {
        assert (player1.getColor() != player2.getColor());

        board = new Checkerboard(maxx, maxy);
        players.add(player1);
        players.add(player2);
    }

    public Player nextPlayer() {
        return players.get((curPlayer + 1) % 2);
    }

    public Player curPlayer() {
        return players.get(curPlayer);
    }

    public void turnToNextPlayer() {
        curPlayer = (curPlayer + 1) % 2;
    }

    public Player getWinner() {
        return players.stream().max((e1, e2) -> {
            return Long.valueOf(board.allPositionsOfColor(e1.getColor()).count())
                    .compareTo(Long.valueOf(board.allPositionsOfColor(e2.getColor()).count()));
        }).get();
    }

    // 两个玩家都没有落子的位置
    public boolean finished() {
        return !board.getAttactingPosition(nextPlayer().getColor()).isPresent()
                && !board.getAttactingPosition(curPlayer().getColor()).isPresent();
    }

    public Checkerboard getBoard() {
        return board;
    }

    public void start() {
        startTime = new Date();
    }

    public void finish() {
        finishTime = new Date();
    }

}
