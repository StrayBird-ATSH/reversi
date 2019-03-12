import java.util.Scanner;

public class Person implements Playable {

    private Piece piece;

    Person() {
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public boolean placeable(Board board) {
        return piece.placeable(board, true);
    }

    @Override
    public void place(Board board) {
        piece.personPrompt();
        Scanner input = new Scanner(System.in);
        String string = input.nextLine();
        int row = string.charAt(0) - 'a';
        int column = string.charAt(1) - 'a';
        if (row < 0 || row >= board.size || column < 0 || column >= board.size) {
            System.out.println("Your input is illegal.");
            System.exit(0);
        }
        if (board.calculateScore(row, column, true) == 0) {
            if (piece instanceof BlackPiece) System.out.println("Invalid move.\nGame Over.\nO player wins.");
            else System.out.println("Invalid move.\nGame Over.\nX player wins.");
            System.exit(0);
        } else {
            board.flip(row, column, piece.color == Color.WHITE ? Color.BLACK : Color.WHITE);
            System.out.println(board);
        }
    }
}
