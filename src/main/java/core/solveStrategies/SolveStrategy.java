package core.solveStrategies;

public interface SolveStrategy {

    /**
     * Applies the startegie IN PLACE to the given Sudoku-field
     * @param field the given Sudoku-field
     * @return the field after the strategie is applied
     */
    int[][] applyTo(int[][] field);
}
