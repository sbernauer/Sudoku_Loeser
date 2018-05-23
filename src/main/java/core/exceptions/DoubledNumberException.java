package core.exceptions;

/**
 * Represents a doubled number in a row, column or block in the Sudoku.
 */
public class DoubledNumberException extends RuntimeException {
    private int row;
    private int column;

    public DoubledNumberException(int numberValue, int row, int column) {
        super("Doppelte Zahl " + numberValue + " an Position " + row + ", " + column + ".");
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
