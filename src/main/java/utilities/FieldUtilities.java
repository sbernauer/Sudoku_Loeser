package utilities;

import core.exceptions.DoubledNumberException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static main.Gui.FIELD_SIZE;

public class FieldUtilities {

    public static final Set<Integer> ALL_NUMBERS = IntStream.range(1, 10).boxed().collect(Collectors.toSet());

    public static int[][] cloneField(int[][] source) {
        int[][] result = new int[FIELD_SIZE][FIELD_SIZE];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = source[x][y];
            }
        }
        return result;
    }

    /**
     * @param field the given sudoku-field
     * @param row the row were the numbers are calculated from
     * @param checkForDuplicates if true it throws a DoubledNumberException when a number is doubled in this row
     * @return Set of Numbers in this row
     */
    public static Set<Integer> getNumbersInRow(int[][] field, int row, boolean checkForDuplicates) {
        Set<Integer> foundNumbers = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (checkForDuplicates && field[row][i] != -1 && foundNumbers.contains(field[row][i])) {
                throw new DoubledNumberException(field[row][i], row, i);
            }
            foundNumbers.add(field[row][i]);
        }
        foundNumbers.remove(-1);
        return foundNumbers;
    }

    /**
     * @param field the given sudoku-field
     * @param column the column were the numbers are calculated from
     * @param checkForDuplicates if true it throws a DoubledNumberException when a number is doubled in this column
     * @return Set of Numbers in this column
     */
    public static Set<Integer> getNumbersInColumn(int[][] field, int column, boolean checkForDuplicates) {
        Set<Integer> foundNumbers = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (checkForDuplicates && field[i][column] != -1 && foundNumbers.contains(field[i][column])) {
                throw new DoubledNumberException(field[i][column], i, column);
            }
            foundNumbers.add(field[i][column]);
        }
        foundNumbers.remove(-1);
        return foundNumbers;
    }

    /**
     * @param field the given sudoku-field
     * @param positionX the x-Coordinate of any point in the block
     * @param positionY the y-Coordinate of any point in the block
     * @param checkForDuplicates if true it throws a DoubledNumberException when a number is doubled in this block
     * @return Set of Numbers in this block
     */
    public static Set<Integer> getNumbersInBlock(int[][] field, int positionX, int positionY, boolean checkForDuplicates) {
        int startX = (int) (positionX / 3) * 3;
        int startY = (int) (positionY / 3) * 3;

        Set<Integer> foundNumbers = new HashSet<>();
        for (int x = startX; x < startX + 3; x++) {
            for (int y = startY; y < startY + 3; y++) {
                if (checkForDuplicates && field[x][y] != -1 && foundNumbers.contains(field[x][y])) {
                    throw new DoubledNumberException(field[x][y], x, y);
                }
                foundNumbers.add(field[x][y]);
            }
        }
        foundNumbers.remove(-1);
        return foundNumbers;
    }

    /**
     * @param field the given sudoku-field
     * @param row the row were the missing numbers are calculated from
     * @return Set of Numbers that are missing in this row
     */
    public static Set<Integer> getMissingNumbersInRow(int[][] field, int row) {
        Set<Integer> foundNumbers = getNumbersInRow(field, row, false);
        Set<Integer> missingNumbers = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            if (!foundNumbers.contains(i)) {
                missingNumbers.add(i);
            }
        }
        return missingNumbers;
    }

    /**
     * @param field the given sudoku-field
     * @param column the column were the missing numbers are calculated from
     * @return Set of Numbers that are missing in this column
     */
    public static Set<Integer> getMissingNumbersInColumn(int[][] field, int column) {
        Set<Integer> foundNumbers = getNumbersInColumn(field, column, false);
        Set<Integer> missingNumbers = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            if (!foundNumbers.contains(i)) {
                missingNumbers.add(i);
            }
        }
        return missingNumbers;
    }

    /**
     * @param field the given sudoku-field
     * @param positionX the x-Coordinate of any point in the block
     * @param positionY the y-Coordinate of any point in the block
     * @return Set of Numbers that are missing in this block
     */
    public static Set<Integer> getMissingNumbersInBlock(int[][] field, int positionX, int positionY) {
        Set<Integer> foundNumbers = getNumbersInBlock(field, positionX, positionY, false);
        Set<Integer> missingNumbers = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            if (!foundNumbers.contains(i)) {
                missingNumbers.add(i);
            }
        }
        return missingNumbers;
    }

    /**
     * Takes a Set of numbers that should be inserted into a field.
     * It only inserts the Number if it's the only possible number in this field.
     * @param field the given sudoku-field
     * @param positionX the x-Coordinate of the field where the number should be inserted
     * @param positionY the y-Coordinate of the field where the number should be inserted
     * @param possibleNumbers the numbers that should be tried to be inserted into the field
     * @return -1 if no fitting number was found, otherwise the number that was put in the field
     */
    public static int writePossibleNumberInField(int[][] field, int positionX, int positionY, Set<Integer> possibleNumbers) {
        Set<Integer> possibilities = getPossibilitiesForField(field, positionX, positionY, possibleNumbers);

        if (possibilities.size() == 1) {
            int number = possibilities.iterator().next();
            field[positionX][positionY] = number;
            return number;
        }
        return -1;
    }

    /**
     * @param field the given sudoku-field
     * @param positionX the x-Coordinate of the field
     * @param positionY the y-Coordinate of the field
     * @param possibleNumbers the numbers that should be tried to be inserted into the field
     * @return All numbers that are possible in this field
     */
    public static Set<Integer> getPossibilitiesForField(int[][] field, int positionX, int positionY, Set<Integer> possibleNumbers) {
        Set<Integer> result = new HashSet<>();
        result.addAll(possibleNumbers);

        Set<Integer> foundNumbersInRow = getNumbersInRow(field, positionX, false);
        Set<Integer> foundNumbersInColumn = getNumbersInColumn(field, positionY, false);
        Set<Integer> foundNumbersInBlock = getNumbersInBlock(field, positionX, positionY, false);
        result.removeAll(getNumbersInRow(field, positionX, false));
        result.removeAll(getNumbersInColumn(field, positionY, false));
        result.removeAll(getNumbersInBlock(field, positionX, positionY, false));

        return result;
    }

    /**
     * @param field the given sudoku-field
     * @param positionX the x-Coordinate of the field
     * @param positionY the y-Coordinate of the field
     * @return All numbers that are possible in this field
     */
    public static Set<Integer> getPossibilitiesForField(int[][] field, int positionX, int positionY) {
        return getPossibilitiesForField(field, positionX, positionY, ALL_NUMBERS);
    }

    public static boolean isNumberPossibleInField(int[][] field, int positionX, int positionY, int numberToCheck) {
        Set<Integer> numbersToCheck = new HashSet<>();
        numbersToCheck.add(numberToCheck);
        return getPossibilitiesForField(field, positionX, positionY, numbersToCheck).contains(numberToCheck);
    }

    /**
     * @param field the sudoku-field
     * @throws DoubledNumberException if the sudoku is invalid, the cause is given in the message
     */
    public static void checkForValidSudoku(int[][] field) throws DoubledNumberException {
        for (int i = 0; i < 9; i++) {
            getNumbersInRow(field, i, true);
            getNumbersInColumn(field, i, true);
        }
        for (int x = 0; x < 9; x += 3) {
            for (int y = 0; y < 9; y += 3) {
                getNumbersInBlock(field, x, y, true);
            }
        }
    }

    public static int[][] getEmptyField() {
        int[][] field = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                field[x][y] = -1;
            }
        }
        return field;
    }
}
