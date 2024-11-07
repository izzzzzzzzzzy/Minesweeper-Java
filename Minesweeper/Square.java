package Minesweeper;

/**
 * Represents a Square, which is extended by both MineSquare and NumberSquare
 */
public abstract class Square {
    private String element;
    private boolean flagged;
    private boolean uncovered;

    /**
     * Default constructor
     */
    public Square() {
    }

    /**
     * Creates a Square with specified element, flagged status, and unvovered status
     * 
     * @param element   What the Square has inside of it
     * @param flagged   Whether or not the Square is flagged
     * @param uncovered Whether or not the Square is uncovered
     */
    public Square(String element, boolean flagged, boolean uncovered) {
        this.element = element;
        this.flagged = flagged;
        this.uncovered = uncovered;
    }

    /**
     * Sets the element of the square
     * 
     * @param element the new element to set
     */
    public void setElement(String element) {
        this.element = element;
    }

    /**
     * Returns whether or not the Square is flagged
     * 
     * @return whether or not the Square is flagged
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Returns whether or not the Square is uncovered
     * 
     * @return whether or not the Square is uncovered
     */
    public boolean isUncovered() {
        return uncovered;
    }

    /**
     * Changes the square from uncovered to covered, and vice versa
     */
    public void setUncovered() {
        this.uncovered = !this.uncovered;
    }

    /**
     * Changes the square from flagged to unflagged, and vice versa
     */
    public void flagSquare() {
        this.flagged = !this.flagged;
    }

    /**
     * Uncoveres a square so long as it is not flagged
     */
    public abstract void uncover();

    /**
     * Returns whether or not the square is a mine
     * 
     * @return whether or not the square is a mine
     */
    public abstract boolean isMine();

    /**
     * Returns a string representation of the Square
     * 
     * @return a string representation of the Square
     */
    @Override
    public String toString() {
        if (isFlagged())
            return "f";
        if (!isUncovered())
            return "x";
        if (isMine())
            return "*";
        return element;
    }

}
