package core.bruteforcing;

import utilities.BruteForceUtilities;
import utilities.FieldUtilities;

import java.util.List;

public class BruteForcing1 implements BruteForceStrategy {

    @Override
    public int[][] bruteForce(int[][] field) {
        List<BruteForceField> bruteForceFields = BruteForceUtilities.getBruteForceFields(field);

        System.out.println("Brute-forcing " + bruteForceFields.size() + " fields...");

        BruteForceUtilities.setNumberForAllBruceForceFields(field, bruteForceFields, 1);

        do {
            int i = 0;
            do {
                BruteForceField bff = bruteForceFields.get(i);
                field[bff.x][bff.y] = bff.getNextNumber();
                if (bff.currentNumberIndex == 0) { // Did complete loop
                    i++;
                } else {
                    break;
                }
            } while (i < bruteForceFields.size());

        } while (!FieldUtilities.isValidSudoku(field));

        return field;
    }
}
