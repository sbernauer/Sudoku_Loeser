package core.solveStrategies;

import utilities.FieldUtilities;

import java.util.Set;

public class RowStrategy implements SolveStrategy {

    @Override
    public int[][] applyTo(int[][] field) {
        for (int x = 0; x < 9; x++) {
            Set<Integer> missingNumbers = FieldUtilities.getMissingNumbersInRow(field, x);
            for (int y = 0; y < 9; y++) {
                if (field[x][y] == -1) {
                    FieldUtilities.writePossibleNumberInCell(field, x, y, missingNumbers);
                }
            }
        }

        return field;
    }
}
