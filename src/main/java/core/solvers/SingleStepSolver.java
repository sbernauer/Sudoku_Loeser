package core.solvers;

import core.solveStrategies.*;
import utilities.FieldUtilities;

import java.util.ArrayList;
import java.util.List;

import static main.Gui.FIELD_SIZE;

public class SingleStepSolver {
    private static final List<SolveStrategy> ALL_STRATEGIES = new ArrayList<SolveStrategy>() {{
        add(new RowStrategy());
        add(new ColumnStrategy());
        add(new BlockStrategy());
        add(new MissingThirdNumberStrategy());
        add(new NakedSingleStrategy());
    }};

    /**
     * Applies all the strategies for one step
     *
     * @param field the given Sudoku-field
     * @return a new field with the one step solved Sudoku
     */
    public static int[][] solveOneStep(int[][] field) {
        int[][] result = FieldUtilities.cloneField(field);

        for (SolveStrategy strategy : ALL_STRATEGIES) {
            int[][] oneStepWithStrategie = strategy.applyTo(FieldUtilities.cloneField(field));
            for (int x = 0; x < FIELD_SIZE; x++) {
                for (int y = 0; y < FIELD_SIZE; y++) {
                    if (oneStepWithStrategie[x][y] != field[x][y]) {
                        result[x][y] = oneStepWithStrategie[x][y];
                    }
                }
            }
        }

        return result;
    }
}
