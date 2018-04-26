package core;

public class DoubledNumberException extends RuntimeException {
    private int row;
    private int column;
    private int numberValue;

    public DoubledNumberException(int numberValue, int row, int column) {
        super("Doppelte Zahl " + numberValue + " an Position " + row + ", " + column + ".");
        this.row = row;
        this.column = column;
        this.numberValue = numberValue;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getNumberValue() {
        return numberValue;
    }
}
