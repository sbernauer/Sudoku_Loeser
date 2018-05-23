package core.solvers;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BackTrackSolverTest {
    // Diese Testdaten entstammen goesstenteils Sudokus aus Zeitschriften, welche von Hand geloest wurden
    private static final int[][] EASY = {{2, 6, 5, -1, -1, -1, 9, 3, 8,}, {1, 8, 3, 5, 9, 6, 7, 4, 2,}, {4, 7, 9, 2, 3, 8, 6, 5, 1,}, {9, 3, 6, 4, 5, 1, 2, 8, 7,}, {7, 4, 8, 6, 2, 3, 5, 1, 9,}, {5, 2, 1, 7, 8, 9, 3, 6, 4,}, {6, 5, 4, 9, 1, 2, 8, 7, 3,}, {3, 1, 2, 8, 7, 5, 4, 9, 6,}, {8, 9, 7, 3, 6, 4, 1, 2, 5,}};
    private static final int[][] EASY_SOLVED = {{2, 6, 5, 1, 4, 7, 9, 3, 8,}, {1, 8, 3, 5, 9, 6, 7, 4, 2,}, {4, 7, 9, 2, 3, 8, 6, 5, 1,}, {9, 3, 6, 4, 5, 1, 2, 8, 7,}, {7, 4, 8, 6, 2, 3, 5, 1, 9,}, {5, 2, 1, 7, 8, 9, 3, 6, 4,}, {6, 5, 4, 9, 1, 2, 8, 7, 3,}, {3, 1, 2, 8, 7, 5, 4, 9, 6,}, {8, 9, 7, 3, 6, 4, 1, 2, 5,}};

    private static final int[][] HARD = {{-1, -1, -1, 6, -1, 9, -1, -1, 4,}, {-1, -1, 7, -1, -1, -1, -1, 9, 1,}, {-1, 4, 8, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, 4, -1, -1, -1, 3, -1,}, {2, -1, -1, -1, 6, -1, -1, -1, 7,}, {-1, 9, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, 5, -1, -1, 1, 4, -1,}, {6, 3, -1, -1, -1, -1, 9, -1, -1,}, {7, -1, -1, 1, -1, 6, -1, -1, -1,}};
    private static final int[][] HARD_SOLVED = {{3, 1, 2, 6, 5, 9, 7, 8, 4,}, {5, 6, 7, 3, 8, 4, 2, 9, 1,}, {9, 4, 8, 2, 1, 7, 5, 6, 3,}, {1, 7, 6, 4, 2, 5, 8, 3, 9,}, {2, 8, 3, 9, 6, 1, 4, 5, 7,}, {4, 9, 5, 7, 3, 8, 6, 1, 2,}, {8, 2, 9, 5, 7, 3, 1, 4, 6,}, {6, 3, 1, 8, 4, 2, 9, 7, 5,}, {7, 5, 4, 1, 9, 6, 3, 2, 8,}};

    private static final int[][] VERY_HARD = {{2, 6, 5, -1, -1, -1, 9, -1, -1,}, {-1, -1, -1, -1, 9, 6, -1, 4, -1,}, {4, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, 3, -1, -1, -1, -1, -1, -1, 7,}, {-1, -1, -1, 6, 2, 3, -1, -1, -1,}, {5, -1, -1, -1, -1, -1, -1, 6, -1,}, {-1, -1, -1, 9, -1, -1, -1, -1, 3,}, {-1, 1, -1, 8, 7, -1, -1, -1, -1,}, {-1, -1, 7, -1, -1, -1, 1, 2, 5,}};
    private static final int[][] VERY_HARD_SOLVED = {{2, 6, 5, 1, 4, 7, 9, 3, 8,}, {1, 8, 3, 5, 9, 6, 7, 4, 2,}, {4, 7, 9, 2, 3, 8, 6, 5, 1,}, {9, 3, 6, 4, 5, 1, 2, 8, 7,}, {7, 4, 8, 6, 2, 3, 5, 1, 9,}, {5, 2, 1, 7, 8, 9, 3, 6, 4,}, {6, 5, 4, 9, 1, 2, 8, 7, 3,}, {3, 1, 2, 8, 7, 5, 4, 9, 6,}, {8, 9, 7, 3, 6, 4, 1, 2, 5,}};

    private static final int[][] NOT_SOLVABLE = {{2, 6, 5, -1, -1, -1, 9, -1, -1,}, {-1, -1, -1, -1, 9, 6, -1, 4, -1,}, {4, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, 3, -1, -1, -1, -1, -1, -1, 7,}, {1, -1, -1, 6, 2, 3, -1, -1, -1,}, {5, -1, -1, -1, -1, -1, -1, 6, -1,}, {-1, -1, -1, 9, -1, -1, -1, -1, 3,}, {-1, 1, -1, 8, 7, -1, -1, -1, -1,}, {-1, -1, 7, -1, -1, -1, 1, 2, 5,}};

    @Test(expected = IllegalArgumentException.class)
    public void testSolveComplete_invalidSizeGiven_throwIllegalArgumentException() {
        BackTrackSolver.solveComplete(new int[][]{});
    }

    @Test
    public void testSolveComplete_validSudokuGiven_solveEasySudoku() {
        int[][] result = BackTrackSolver.solveComplete(EASY);
        assertArrayEquals(result, EASY_SOLVED);
    }

    @Test
    public void testSolveComplete_validSudokuGiven_solveHardSudoku() {
        int[][] result = BackTrackSolver.solveComplete(HARD);
        assertArrayEquals(result, HARD_SOLVED);
    }

    @Test
    public void testSolveComplete_validSudokuGiven_solveVeryHardSudoku() {
        int[][] result = BackTrackSolver.solveComplete(VERY_HARD);
        assertArrayEquals(result, VERY_HARD_SOLVED);
    }

    @Test
    public void testSolveComplete_notSolvableSudokuGiven_returnNull() {
        int[][] result = BackTrackSolver.solveComplete(NOT_SOLVABLE);
        assertEquals(result, null);
    }
}