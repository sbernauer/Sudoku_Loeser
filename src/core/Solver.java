package core;

import core.solveStrategies.*;
import utilities.FieldUtilities;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private static final List<SolveStrategy> ALL_STRATEGIES = new ArrayList<SolveStrategy>() {{
        add(new RowStrategy());
        add(new ColumnStrategy());
        add(new BlockStrategy());
        add(new MissingThirdNumberStrategy());
        add(new NakedSingleStrategy());
    }};

    public static int[][] applyAllStrategies(int[][] field, List<SolveStrategy> solveStrategies) {
        int[][] result = field;
        int[][] before;

        do {
            before = result;
            result = FieldUtilities.cloneArray(result);

            for (SolveStrategy strategy : solveStrategies) {
                result = strategy.applyTo(result);
            }
        }
        while (!FieldUtilities.areEqual(before, result)); // Until all strategies can't get further any more

        FieldUtilities.checkForValidSudoku(result);
        System.out.println("Ergebnis ist valide.");

        return result;
    }

    public static int[][] solveComplete(int[][] field) {
        return applyAllStrategies(field, ALL_STRATEGIES);
    }
}
