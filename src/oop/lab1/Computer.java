package oop.lab1;

//计算机玩家
public class Computer extends Player {

    public Computer(Color color) {
        super(color);
    }

    @Override
    public void action(Session session) {

        //获取当前的棋盘
        Checkerboard board = session.getBoard();

        //获取落子点
        board.getAttactingPosition(getColor()).ifPresent(p -> {

            //放置棋子
            board.put(p, getColor());

        });

    }

}
