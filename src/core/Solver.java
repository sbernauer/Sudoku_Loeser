package core;

import core.bruteforcing.BruteForcer;
import core.solveStrategies.*;
import utilities.FieldUtilities;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static main.Gui.FIELD_SIZE;

public class Solver {
    private static final List<SolveStrategy> ALL_STRATEGIES = new ArrayList<SolveStrategy>() {{
        add(new RowStrategy());
        add(new ColumnStrategy());
        add(new BlockStrategy());
        add(new MissingThirdNumberStrategy());
//        add(new NakedSingleStrategy());
    }};

    public static int[][] applyStrategies(int[][] field) {
        int[][] result = field;
        int[][] before;

        do {
            before = result;
            result = FieldUtilities.cloneArray(result);

            for (SolveStrategy strategy : ALL_STRATEGIES) {
                result = strategy.applyTo(result);
            }
        }
        while (!FieldUtilities.areEqual(before, result)); // Until all strategies can't get further any more

        return result;
    }

    public static int[][] solveComplete(int[][] field, boolean askUserToUseBruteForce) {
        field = applyStrategies(field);
        if (askUserToUseBruteForce && !FieldUtilities.isSudokuCompletelyFilled(field)) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "Das Sudoku konnte mit den implementierten Strategien nicht vollsändig gelöst werden.\nSoll das Sudoku mitttels Brute-Force gelöst werden?\n\n" +
                            "Es sind " + FieldUtilities.getAmountOfEmptyFields(field) + " freie Felder zu lösen",
                    "Titel", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                field = new BruteForcer().bruteForce(field);
            }
        }

        FieldUtilities.checkForValidSudoku(field);
        System.out.println("Ergebnis ist valide.");

        return field;
    }

    public static int[][] solveOneStep(int[][] field) {
        int[][] result = FieldUtilities.cloneArray(field);

        for (SolveStrategy strategy : ALL_STRATEGIES) {
            int[][] oneStepWithStrategie = strategy.applyTo(FieldUtilities.cloneArray(field));
            for (int x = 0; x < FIELD_SIZE; x++) {
                for (int y = 0; y < FIELD_SIZE; y++) {
                    if(oneStepWithStrategie[x][y] != field[x][y]) {
                        result[x][y] = oneStepWithStrategie[x][y];
                    }
                }
            }
        }
        return result;
    }
}
