package core.bruteforcing;

import utilities.BruteForceUtilities;
import utilities.FieldUtilities;

import java.util.List;

public class BruteForcer implements BruteForceStrategy, Runnable {
    private int tiefe;
    private Thread thread;

    public BruteForcer() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public int[][] bruteForce(int[][] field) {
        List<BruteForceField> bruteForceFields = BruteForceUtilities.getBruteForceFields(field);

        System.out.println("Brute-forcing " + bruteForceFields.size() + " fields...");

        BruteForceUtilities.setNumberForAllBruceForceFields(field, bruteForceFields, 1);

        // This loop wil try EVERY combination of the bruceForceFields until a solution is found
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
                tiefe = Math.max(tiefe, i);
            } while (i < bruteForceFields.size());
            if (i >= bruteForceFields.size()) {
                //TODO Error, because there was no possible combination, so that the sudoku was correct
            }

        } while (!FieldUtilities.isValidSudoku(field));

        thread.stop();

        return field;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Tiefe: " + tiefe);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
