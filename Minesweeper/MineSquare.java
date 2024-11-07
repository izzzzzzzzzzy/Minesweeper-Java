package Minesweeper;

/**
 * Represents a type of Square which is a Mine
 */
public class MineSquare extends Square {

    /**
     * If the square is not flagged, reveal it.
     * If it is flagged, throws a RevealFlaggedSquareException
     */
    public void uncover() {
        if (!isFlagged())
            setUncovered();
        else
            throw new RevealFlaggedSquareException();
    }

    /**
     * Returns true because MineSquares are mines
     */
    public boolean isMine() {
        return true;
    }
}
