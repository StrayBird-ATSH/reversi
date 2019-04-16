package oop.lab1.console;

import oop.lab1.Color;
import oop.lab1.Computer;
import oop.lab1.Game;
import oop.lab1.Session;

public class ConsoleGame {

    public static void playOneSession(Session s) {
        s.start();

        while (!s.finished()) {

            ConsoleBoardUtil.display(s.getBoard());

            s.nextPlayer().action(s);

            s.turnToNextPlayer();

            ConsoleBoardUtil.waitforInput();

        }
        ConsoleBoardUtil.display(s.getBoard());
        s.finish();
        System.out.println(s.getWinner().getColor());
    }

    public static void main(String[] args) {

        Game game = new Game();
        Session s = game.startSession(6, 6, new Computer(Color.BLACK), new Computer(Color.WHITE));
        playOneSession(s);


    }

}