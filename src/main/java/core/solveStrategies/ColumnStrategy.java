package core.solveStrategies;

import utilities.FieldUtilities;

import java.util.Set;

/**
 * Searches for a missing number in a column
 */
public class ColumnStrategy implements SolveStrategy {

    @Override
    public int[][] applyTo(int[][] field) {
        for (int y = 0; y < 9; y++) {
            Set<Integer> missingNumbers = FieldUtilities.getMissingNumbersInColumn(field, y);
            for (int x = 0; x < 9; x++) {
                if (field[x][y] == -1) {
                    FieldUtilities.writePossibleNumberInField(field, x, y, missingNumbers);
                }
            }
        }

        return field;
    }
}
