import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Reversi game!\n" +
                "Enter the board dimension:");
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        if (size % 2 != 0 || size > 11) {
            System.out.println("The size should be even and less than 10.");
            System.exit(0);
        }
        Board board = new Board(size);
        System.out.println("Computer plays (X/O): ");
        String string = input.nextLine();
        String promptUser = "";
        String promptCom = "";
        if (string.charAt(0) == 'X') {
            board.setUserColor(Color.WHITE);
            promptUser = "Enter move for O (RowCol):";
            promptCom = "Computer places X at ";
        } else if (string.charAt(0) == 'O') {
            board.setUserColor(Color.BLACK);
            promptUser = "Enter move for X (RowCol):";
            promptCom = "Computer places O at ";
        } else {
            System.out.println("Your input is illegal.");
            System.exit(0);
        }
        boolean computerTurn = !(board.getUserColor() == Color.BLACK);
        while (board.userCount + board.comCount < board.size * board.size) {
            if (computerTurn) {


            } else {
            }


        }
    }

    public static void placePiece() {

    }
}
