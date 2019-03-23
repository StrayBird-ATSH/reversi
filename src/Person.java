import java.util.Scanner;

public class Person implements Playable {

    private Piece piece;

    Person() {
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Checks whether this player can put a piece on the board.
     *
     * @param board The current chess board object.
     * @return true if the player can have valid move and false otherwise
     */
    @Override
    public boolean placeable(Board board) {
        return piece.placeable(board, true);
    }

    /**
     * This method is called to let the player place a piece.
     *
     * @param board The current chess board object.
     */
    @Override
    public void place(Board board) {
        piece.personPrompt();
        Scanner input = new Scanner(System.in);
        String string = input.nextLine();
        if (string.length() != 2 || string.charAt(0) - 'a' < 0 || string.charAt(0) - 'a' >= board.size ||
                string.charAt(1) - 'a' < 0 || string.charAt(1) - 'a' >= board.size ||
                board.calculateScore(string.charAt(0) - 'a', string.charAt(1) - 'a', true) == 0) {
            if (piece instanceof BlackPiece) System.out.println("Invalid move.\nGame Over.\nO player wins.");
            else System.out.println("Invalid move.\nGame Over.\nX player wins.");
            board.writeLog(Main.time, (int) (System.currentTimeMillis() - Main.time), true);
            System.exit(0);
        } else {
            board.flip(string.charAt(0) - 'a', string.charAt(1) - 'a',
                    piece.color == Color.WHITE ? Color.BLACK : Color.WHITE);
            System.out.println(board);
        }
    }
}
