package oop.lab1.console;

import oop.lab1.Checkerboard;
import oop.lab1.Color;
import oop.lab1.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleBoardUtil {
    private static char getSymbol(Color color) {
        switch (color) {
            case BLACK:
                return 'X';
            case WHITE:
                return 'O';
            case BLANK:
                return '.';
            default:
                throw new RuntimeException("invalid color");
        }
    }

    public static char toChar(char i) {
        return (char) (i + 'a');
    }

    public static void display(Checkerboard board) {
        System.out.print(' ');
        for (int y = 0; y < board.getMax_y(); y++) {
            System.out.print((char) (y + 'a'));
            System.out.print(' ');
        }
        System.out.println();
        for (int x = 0; x < board.getMax_x(); x++) {
            System.out.print((char) (x + 'a'));
            for (int y = 0; y < board.getMax_y(); y++) {
                System.out.print(getSymbol(board.get(Position.of(x, y))));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void waitforInput() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
