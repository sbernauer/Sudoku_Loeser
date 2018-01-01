package utilities;

import core.InvalidSudokuException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FieldUtilities {

    public static final Set<Integer> ALL_NUMBERS = IntStream.range(1, 10).boxed().collect(Collectors.toSet());

    public static int[][] cloneArray(int[][] source) {
        int[][] result = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = source[x][y];
            }
        }
        return result;
    }

    public static Set<Integer> getNumbersInRow(int[][] field, int row, boolean checkForDuplicates) {
        Set<Integer> foundNumbers = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (checkForDuplicates && field[row][i] != -1 && foundNumbers.contains(field[row][i])) {
                throw new InvalidSudokuException("Doppelte Zahl " + field[row][i] + " an Position " + row + ", " + i);
            }
            foundNumbers.add(field[row][i]);
        }
        return foundNumbers;
    }

    public static Set<Integer> getNumbersInColumn(int[][] field, int column, boolean checkForDuplicates) {
        Set<Integer> foundNumbers = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (checkForDuplicates && field[i][column] != -1 && foundNumbers.contains(field[i][column])) {
                throw new InvalidSudokuException("Doppelte Zahl " + field[i][column] + " an Position " + i + ", " + column);
            }
            foundNumbers.add(field[i][column]);
        }
        return foundNumbers;
    }

    public static Set<Integer> getNumbersInBlock(int[][] field, int startX, int startY, boolean checkForDuplicates) {
        int blockStartX = (int) (startX / 3) * 3;
        int blockStartY = (int) (startY / 3) * 3;

        Set<Integer> foundNumbers = new HashSet<>();
        for (int x = blockStartX; x < blockStartX + 3; x++) {
            for (int y = blockStartY; y < blockStartY + 3; y++) {
                if (checkForDuplicates && field[x][y] != -1 && foundNumbers.contains(field[x][y])) {
                    throw new InvalidSudokuException("Doppelte Zahl " + field[x][y] + " an Position " + x + ", " + y);
                }
                foundNumbers.add(field[x][y]);
            }
        }
        return foundNumbers;
    }

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

    public static Set<Integer> getMissingNumbersInColumn(int[][] field, int row) {
        Set<Integer> foundNumbers = getNumbersInColumn(field, row, false);
        Set<Integer> missingNumbers = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            if (!foundNumbers.contains(i)) {
                missingNumbers.add(i);
            }
        }
        return missingNumbers;
    }

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
     * @param field
     * @param x
     * @param y
     * @param possibleNumbers
     * @return -1 if no fitting number was found, otherwise the number that was put in the field
     */
    public static int writePossibleNumberInCell(int[][] field, int x, int y, Set<Integer> possibleNumbers) {
        Set<Integer> possibilities = getPossibilitiesForField(field, x, y, possibleNumbers);

        if (possibilities.size() == 1) {
            int number = possibilities.iterator().next();
            field[x][y] = number;
            return number;
        }
        return -1;
    }

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
     * @throws InvalidSudokuException if the sudoku is invalid, the cause is given in the message
     */
    public static void checkForValidSudoku(int[][] field) throws InvalidSudokuException {
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

    public static boolean isValidSudoku(int[][] field) {
        try { //TODO Fix this dirty shit
            checkForValidSudoku(field);
            return true;
        } catch (InvalidSudokuException e) {
            return false;
        }
    }

    public static boolean areEqual(int[][] field1, int[][] field2) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (field1[i][j] != field2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isSudokuCompletelyFilled(int[][] field) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (field[x][y] == -1) {
                    return false;
                }
            }
        }
        return true;
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