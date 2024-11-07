package Minesweeper;

/**
 * Represents a version of a Square that is not a mine
 */
public class NumberSquare extends Square {

    private int neighborMines;

    /**
     * Creates a NumberSquare with specified neighboring mines, row, and column
     * 
     * @param neighborMines number of mines next to the NumberSquare
     * @param myRow         the row of the NumberSquare
     * @param myCol         the column of the NumberSquare
     */
    public NumberSquare(int neighborMines, int myRow, int myCol) {
        super(neighborMines == 0 ? "_" : neighborMines + "", false, false);
        this.neighborMines = neighborMines;
    }

    /**
     * Returns the number of neighboring mines
     * 
     * @return the number of neighboring mines
     */
    public int getNeighborMines() {
        return neighborMines;
    }

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
     * Returns false because NumberSquares are not mines
     */
    public boolean isMine() {
        return false;
    }
}
