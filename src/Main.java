import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Reversi game!\n" +
                "How large do you want the board to be? \n");
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        if (size % 2 != 0 || size > 11) {
            System.out.println("The size should be even and less than 10.");
            System.exit(0);
        }
        Board board = new Board(size);
        System.out.println("Input \"x\" if you wish the computer hold black pieces, \"o\" otherwise.");
        String string = input.nextLine();
        if (string.charAt(0) == 'x') ;
        else if (string.charAt(0) == 'o') ;
        else {
            System.out.println("Your input is illegal.");
            System.exit(0);
        }
    }
}
