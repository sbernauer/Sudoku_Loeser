package core.solveStrategies;

import utilities.FieldUtilities;

import java.util.HashSet;
import java.util.Set;

public class NakedSingleStrategy implements SolveStrategy {

    @Override
    public int[][] applyTo(int[][] field) {
        Set<Integer> allNumbers = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            allNumbers.add(i);
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (field[x][y] == -1) { // Only try for empty fields
                    Set<Integer> possibleNumbers = FieldUtilities.getPossibilitiesForField(field, x, y, allNumbers);
                    if (possibleNumbers.size() == 1) { // Only one number possible
                        field[x][y] = possibleNumbers.iterator().next();
                    }
                }
            }
        }

        return field;
    }
}
