package core.solveStrategies;

import utilities.FieldUtilities;

import java.util.HashSet;
import java.util.Set;

public class NakedSingleStrategy implements SolveStrategy {

    @Override
    public int[][] applyTo(int[][] field) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (field[x][y] == -1) {
                    Set<Integer> possibleNumbers = FieldUtilities.getPossibilitiesForField(field, x, y);
                    if (possibleNumbers.size() == 1) {
                        field[x][y] = possibleNumbers.iterator().next();
                    }
                }
            }
        }

        return field;
    }
}
