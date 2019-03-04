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
                if (!placePiece(board, Player.COMPUTER)) continue;
                System.out.println(promptCom);
                System.out.println(board.toString());

            } else {
                if (!placePiece(board, Player.USER)) continue;
                System.out.println(promptUser);
                String input1 = input.nextLine();
                if (userPutPiece(board, input1)) continue;
                else {
                    System.out.println("Invalid move. ");
                }

            }


        }
    }

    public static boolean placePiece(Board board, Player player) {
        Color userColor = board.getUserColor();
        Color placingPieceColor;
        if (player == Player.USER) placingPieceColor = userColor;
        else placingPieceColor = userColor == Color.BLACK ? Color.WHITE : Color.BLACK;
        int column = 0;
        int row = 0;
        int currentScore = 0;
        int calculatedScore;
        for (int i = 0; i < board.size; i++)
            for (int j = 0; j < board.size; j++) {
                calculatedScore = calculateScore(board, row, column, placingPieceColor);
                if (calculatedScore > currentScore) {
                    row = i;
                    column = j;
                    currentScore = calculatedScore;
                }
            }
        return currentScore != 0;
    }

    public static int calculateScore(Board board, int row, int column, Color placingPieceColor) {
        return 0;
    }

    public static boolean userPutPiece(Board board, String input) {
        return calculateScore(board, (input.charAt(0) - 'a'), (input.charAt(1) - 'a'), board.userColor) != 0;
    }
}
