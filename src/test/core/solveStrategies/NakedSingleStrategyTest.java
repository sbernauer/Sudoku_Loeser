package core.solveStrategies;

import org.junit.jupiter.api.Test;
import utilities.FieldUtilities;

import static org.junit.Assert.assertArrayEquals;

class NakedSingleStrategyTest {
    private static final int[][] EASY = {{1, 2, 3, 4, 5, 6, 7, 8, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};
    private static final int[][] EASY_SOLVED = {{1, 2, 3, 4, 5, 6, 7, 8, 9,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};

    private static final int[][] MEDIUM = {{1, 2, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {7, -1, 8, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};
    private static final int[][] MEDIUM_SOLVED = {{1, 2, 9, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {7, -1, 8, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};

    private static final int[][] HARD = {{1, 2, 3, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};
    private static final int[][] HARD_SOLVED = {{1, 2, 3, -1, -1, 9, -1, -1, -1,}, {-1, -1, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};

    private static final int[][] NOT_SOLVABLE = {{1, 2, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {7, -1, 8, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};

    private NakedSingleStrategy nakedSingleStrategy = new NakedSingleStrategy();

    @Test
    void testApplyTo_easySudokuGiven_applyStrategie() {
        int[][] result = nakedSingleStrategy.applyTo(FieldUtilities.cloneField(EASY));
        assertArrayEquals(result, EASY_SOLVED);
    }

    @Test
    void testApplyTo_mediumSudokuGiven_applyStrategie() {
        int[][] result = nakedSingleStrategy.applyTo(FieldUtilities.cloneField(MEDIUM));
        assertArrayEquals(result, MEDIUM_SOLVED);
    }

    @Test
    void testApplyTo_hardSudokuGiven_applyStrategie() {
        int[][] result = nakedSingleStrategy.applyTo(FieldUtilities.cloneField(HARD));
        assertArrayEquals(result, HARD_SOLVED);
    }

    @Test
    void testApplyTo_notSolvableSudokuGiven_dontApplyStrategie() {
        int[][] result = nakedSingleStrategy.applyTo(FieldUtilities.cloneField(NOT_SOLVABLE));
        assertArrayEquals(result, NOT_SOLVABLE);
    }
}