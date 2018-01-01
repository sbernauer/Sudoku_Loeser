package utilities;

import core.bruteforcing.BruteForceField;

import java.util.ArrayList;
import java.util.List;

public class BruteForceUtilities {

    /**
     * @return a list with the {@link BruteForceField}s which store all numbers that are possible for the field
     */
    public static List<BruteForceField> getBruteForceFields(int[][] field) {
        List<BruteForceField> bruteForceFields = new ArrayList<>();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (field[x][y] == -1) {
                    int[] possibleNumbers = FieldUtilities.getPossibilitiesForField(field, x, y).stream()
                            .mapToInt(Integer::intValue)
                            .toArray();
                    bruteForceFields.add(new BruteForceField(x, y, possibleNumbers));
                }
            }
        }
        return bruteForceFields;
    }

    public static void setNumberForAllBruceForceFields(int[][] field, List<BruteForceField> bruteForceFields, int numberToWrite) {
        for (BruteForceField bruteForceField : bruteForceFields) {
            field[bruteForceField.x][bruteForceField.y] = numberToWrite;
        }
    }
}
