package core.solvers;

import utilities.FieldUtilities;

import static main.Gui.FIELD_SIZE;

public class BackTrackSolver {

    /**
     * @param field the given Sudoku-field
     * @return a new field with the solved Sudoku, or null if the sudoku was not solvable
     */
    public static int[][] solveComplete(int[][] field) {
        if (field.length != FIELD_SIZE || field[0].length != FIELD_SIZE)
            throw new IllegalArgumentException("The dimensions of the field must be " + FIELD_SIZE + " x " + FIELD_SIZE);
        int[][] result = FieldUtilities.cloneField(field);
        if (solve(0, 0, result)) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Solves the given field in place
     *
     * @param x     the x-Coorinate of the cell were the backtraking starts. Normally this is "0"
     * @param y     the y-Coorinate of the cell were the backtraking starts. Normally this is "0"
     * @param field the Sudoku-field
     * @return true if sudoku could be solved, false if not
     */
    private static boolean solve(int x, int y, int[][] field) {
        if (x == FIELD_SIZE) {
            x = 0;
            y++;
            if (y == FIELD_SIZE) {
                return true;
            }
        }
        if (field[x][y] != -1) {
            return solve(x + 1, y, field);
        }

        for (int val = 1; val <= FIELD_SIZE; ++val) {
            if (isValid(x, y, val, field)) {
                field[x][y] = val;
                if (solve(x + 1, y, field)) {
                    return true;
                }
            }
        }
        field[x][y] = -1;
        return false;
    }

    /**
     * @param x     the x-Coordinate for the field to test
     * @param y     the y-Coordinate for the field to test
     * @param value the value for the field to test
     * @param field the Sudoku-field
     * @return true if the value is allowed at this postition, false if not
     */
    private static boolean isValid(int x, int y, int value, int[][] field) {
        for (int k = 0; k < FIELD_SIZE; ++k) {
            if (value == field[k][y]) {
                return false;
            }
        }

        for (int k = 0; k < FIELD_SIZE; ++k) {
            if (value == field[x][k]) {
                return false;
            }
        }

        int boxX = (x / 3) * 3; // Seems useless, but is a Ganzzahldivision
        int boxY = (y / 3) * 3;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (value == field[boxX + i][boxY + j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
