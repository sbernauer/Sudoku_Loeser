package core.exceptions;

/**
 * Represents a error at the paring of the sudoku-fields, for example a letter in an textfield or a number greater then 10.
 */
public class ParseExeption extends Exception {

    public ParseExeption(String message) {
        super(message);
    }
}
