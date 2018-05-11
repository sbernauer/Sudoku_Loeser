package core.solveStrategies;

import core.solvers.SingleStepSolver;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;

class SingleStepSolverTest {
    private static final int[][] EASY = {{1, 2, 3, 4, 5, 6, 7, 8, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};
    private static final int[][] EASY_SOLVED = {{1, 2, 3, 4, 5, 6, 7, 8, 9,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};

    private static final int[][] MEDIUM = {{1, 2, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {7, -1, 8, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};
    private static final int[][] MEDIUM_SOLVED = {{1, 2, 9, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {7, -1, 8, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};

    private static final int[][] HARD = {{1, 2, 3, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};
    private static final int[][] HARD_SOLVED = {{1, 2, 3, -1, -1, 9, -1, -1, -1,}, {-1, -1, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, 3, -1, -1, -1, -1, -1, -1,}};

    private static final int[][] VERY_HARD = {{-1, -1, -1, 6, -1, 9, -1, -1, 4,}, {-1, -1, 7, -1, -1, -1, -1, 9, 1,}, {-1, 4, 8, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, 4, -1, -1, -1, 3, -1,}, {2, -1, -1, -1, 6, -1, -1, -1, 7,}, {-1, 9, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, 5, -1, -1, 1, 4, -1,}, {6, 3, -1, -1, -1, -1, 9, -1, -1,}, {7, -1, -1, 1, -1, 6, -1, -1, -1,}};
    private static final int[][] VERY_HARD_SOLVED = {{-1, -1, -1, 6, -1, 9, -1, -1, 4,}, {-1, -1, 7, -1, -1, -1, -1, 9, 1,}, {9, 4, 8, -1, -1, 7, -1, -1, -1,}, {-1, 7, -1, 4, -1, -1, -1, 3, 9,}, {2, -1, -1, 9, 6, -1, -1, -1, 7,}, {-1, 9, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, 5, -1, -1, 1, 4, 6,}, {6, 3, 1, -1, -1, -1, 9, -1, -1,}, {7, -1, -1, 1, -1, 6, -1, -1, -1,}};

    private static final int[][] NOT_SOLVABLE = {{1, 2, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {7, -1, 8, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};

    @Test
    void testSolveOneStep_easySudokuGiven_solveOneStep() {
        int[][] result = SingleStepSolver.solveOneStep(EASY);
        assertArrayEquals(result, EASY_SOLVED);
    }

    @Test
    void testSolveOneStep_mediumSudokuGiven_solveOneStep() {
        int[][] result = SingleStepSolver.solveOneStep(MEDIUM);
        assertArrayEquals(result, MEDIUM_SOLVED);
    }

    @Test
    void testSolveOneStep_hardSudokuGiven_solveOneStep() {
        int[][] result = SingleStepSolver.solveOneStep(HARD);
        assertArrayEquals(result, HARD_SOLVED);
    }

    @Test
    void testSolveOneStep_veryHardSudokuGiven_solveOneStep() {
        int[][] result = SingleStepSolver.solveOneStep(VERY_HARD);
        assertArrayEquals(result, VERY_HARD_SOLVED);
    }

    @Test
    void testSolveOneStep_notSolvableSudokuGiven_doNothing() {
        int[][] result = SingleStepSolver.solveOneStep(NOT_SOLVABLE);
        assertArrayEquals(result, NOT_SOLVABLE);
    }
}