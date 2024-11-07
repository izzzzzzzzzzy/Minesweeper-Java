package Minesweeper;

import java.util.Random;

public class Grid {

    private Square[][] grid;
    private int width;
    private int height;
    private int numMines;
    private int numSquaresUncovered;

    // Class constants for numbers representing a general move, a winning game
    // state, and a lost game state
    public static final int OK = 0;
    public static final int WIN = 1;
    public static final int MINE = 2;

    /**
     * Creates a Grid with specified width, height, and number of mines
     * 
     * @param width    the width of the board
     * @param height   the height of the board
     * @param numMines the number of mines to create on the board
     */
    public Grid(int width, int height, int numMines) {
        this.width = width;
        this.height = height;
        this.numMines = numMines;

        grid = new Square[height][width];

        Random random = new Random();
        int ct = 0;
        int row, col;

        // Picks random spots on the board and creates MineSquares at each spot,
        // ensuring it does not pick the same place twice
        do {
            row = random.nextInt(height);
            col = random.nextInt(width);

            if (grid[row][col] == null) {
                grid[row][col] = new MineSquare();
                ct++;
            }

        } while (ct < numMines);

        // Nested for loop to step through the Grid, and creates NumberSquares anywhere
        // that is not a MineSquare
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (grid[r][c] == null)
                    grid[r][c] = new NumberSquare(getNeighbors(r, c), r, c);
            }
        }
    }

    /**
     * Returns the number of adjacent squares to a specified location that are
     * unflagged mines
     * 
     * @param row the row of the desired location
     * @param col the column of the desired location
     * @return the number of mines adjacent to the specified row and column
     */
    private int getNeighbors(int row, int col) {
        int ct = 0;

        // Nested for loop to step through the 3x3 area around the specified location
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {

                // Checks to ensure both r and c are in bounds
                if (r >= 0 && r < height && c >= 0 && c < width) {

                    // Makes sure grid[r][c] is no null, and then adds to the count if it is a mine,
                    // but subracts from it if it is flagged
                    if (grid[r][c] != null) {
                        if (grid[r][c].isMine())
                            ct++;
                        if (grid[r][c].isFlagged())
                            ct--;
                    }
                }
            }
        }

        return ct;
    }

    /**
     * Uncovers a specified square, and if the square has no unflagged mines next to
     * it, uncovers all adjacent squares recursively
     * 
     * @param row the row of the desired square
     * @param col the column of the desired square
     * @return an integer that varies depending on the game state after the move. 0
     *         if gameplay continues, 1 if the player won, and 2 if the player lost
     */
    public int uncoverSquare(int row, int col) {
        Square sq = grid[row][col];

        int ret = 0;

        // Exits early and returns MINE if the player tries to reveal an unflagged
        // mine
        if (sq.isMine() && !sq.isFlagged())
            return MINE;

        // Increments numSquaresUncovered if the square has not been uncovered yet
        if (!sq.isUncovered())
            numSquaresUncovered++;
        sq.uncover();

        // If there are no unflagged neightboring mines, enters the for loop
        if (getNeighbors(row, col) == 0) {

            // Loops through the 3x3 area surrounding the uncovered square
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    // Checks to make sure both r and c are in bounds
                    if (r >= 0 && r < height && c >= 0 && c < width) {

                        // Skips over the starting square to avoid an infinite loop
                        if (r != row || c != col) {
                            // If grid[r][c] is still covered and not flagged, recurses with that square as
                            // the location, and sums the result with ret
                            if (!grid[r][c].isUncovered() && !grid[r][c].isFlagged())
                                ret += uncoverSquare(r, c);
                        }
                    }
                }
            }
        }

        // Checks if enough squares have been uncovered where the player has won, and
        // returns WIN if so
        if (numSquaresUncovered == width * height - numMines)
            return WIN;
        // Returns ret, which is the sum of all of the recurses on the original square
        return ret;
    }

    /**
     * Exposes all mines, flagged or unflagged, for when the game ends
     * 
     * @throws RevealFlaggedSquareException
     */
    public void exposeMines() throws RevealFlaggedSquareException {
        for (Square[] squares : grid) {
            for (Square square : squares) {
                if (square.isMine() && !square.isFlagged())
                    square.uncover();
            }
        }
    }

    /**
     * Inverts the flag state of a specified square (if it is flagged, unflags it,
     * if it is not flagged, flags it)
     * 
     * @param row the row of the desired square
     * @param col the column of the desired square
     */
    public void flagSquare(int row, int col) {
        grid[row][col].flagSquare();
    }

    /**
     * Returns a String representation of the Grid
     * 
     * @return a String representation of the Grid
     */
    @Override
    public String toString() {

        String ret = "  ";
        for (int i = 0; i < width; i++) {
            ret += String.format("%3d", i);
        }
        ret += "\n";

        for (int r = 0; r < grid.length; r++) {
            ret += String.format("%2d", r);
            for (int c = 0; c < grid[0].length; c++) {
                ret += "  " + grid[r][c].toString();
            }
            ret += "\n";
        }
        return ret;
    }

}
