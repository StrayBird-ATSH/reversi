/**
 * This interface makes the unified place piece method available no matter
 * a person or a computer is playing the game.
 * Furthermore, it may function more in the future if the strategy is changed
 * so that there is not one person to one computer playing the game.
 * Any member interacting in the game can implement the Playable interface.
 *
 * @author Wang, Chen
 */
public interface Playable {
    /**
     * This method is called to let the player place a piece.
     *
     * @param board The current chess board object.
     */
    void place(Board board);

    /**
     * Checks whether this player can put a piece on the board.
     *
     * @param board The current chess board object.
     * @return true if the player can have valid move and false otherwise
     */
    boolean placeable(Board board);
}
