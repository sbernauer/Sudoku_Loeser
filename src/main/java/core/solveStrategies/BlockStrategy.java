package core.solveStrategies;

import utilities.FieldUtilities;

import java.util.Set;

/**
 * Searches for a missing number in a block
 */
public class BlockStrategy implements SolveStrategy {

    @Override
    public int[][] applyTo(int[][] field) {
        for (int x = 0; x < 9; x += 3) {
            for (int y = 0; y < 9; y += 3) {
                Set<Integer> missingNumbers = FieldUtilities.getMissingNumbersInBlock(field, x, y);
                for (int i = x; i < x + 3; i++) {
                    for (int j = y; j < y + 3; j++) {
                        if (field[i][j] == -1) {
                            int result = FieldUtilities.writePossibleNumberInField(field, i, j, missingNumbers);
                            if (result != -1) {
                                missingNumbers.remove(result);
                            }
                        }
                    }
                }
            }
        }

        return field;
    }
}
