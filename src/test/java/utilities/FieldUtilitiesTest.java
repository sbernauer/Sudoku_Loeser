package utilities;

import core.exceptions.DoubledNumberException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FieldUtilitiesTest {
    private static final int[][] VALID_SAMPLE = {{1, 2, 3, -1, -1, 9, -1, -1, -1,}, {-1, -1, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};
    private static final int[][] INVALID_SAMPLE = {{1, 2, 3, 3, -1, 9, -1, -1, -1,}, {-1, -1, -1, 4, 5, 6, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, 7, -1, -1, -1,}, {-1, -1, -1, -1, -1, 8, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}, {-1, -1, -1, -1, -1, -1, -1, -1, -1,}};

    @Test
    public void cloneField() {
        int[][] clonedField = FieldUtilities.cloneField(VALID_SAMPLE);
        assert(VALID_SAMPLE != clonedField);
        assertEquals(VALID_SAMPLE, clonedField);
    }

    @Test
    public void getNumbersInRow() {
        Set<Integer> numbersInRow = FieldUtilities.getNumbersInRow(VALID_SAMPLE, 0, false);
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 3, 9));
        assertEquals(expected, numbersInRow);

        numbersInRow = FieldUtilities.getNumbersInRow(VALID_SAMPLE, 7, false);
        expected = new HashSet<>();
        assertEquals(expected, numbersInRow);
    }

    @Test
    public void getNumbersInColumn() {
        Set<Integer> numbersInRow = FieldUtilities.getNumbersInColumn(VALID_SAMPLE, 0, false);
        Set<Integer> expected = new HashSet<>(Arrays.asList(1));
        assertEquals(expected, numbersInRow);

        numbersInRow = FieldUtilities.getNumbersInColumn(VALID_SAMPLE, 7, false);
        expected = new HashSet<>();
        assertEquals(expected, numbersInRow);
    }

    @Test
    public void getNumbersInBlock() {
        Set<Integer> numbersInRow = FieldUtilities.getNumbersInBlock(VALID_SAMPLE, 0, 0, false);
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 3));
        assertEquals(expected, numbersInRow);

        numbersInRow = FieldUtilities.getNumbersInBlock(VALID_SAMPLE, 3, 0, false);
        expected = new HashSet<>();
        assertEquals(expected, numbersInRow);
    }

    @Test
    public void getMissingNumbersInRow() {
        Set<Integer> numbersInRow = FieldUtilities.getMissingNumbersInRow(VALID_SAMPLE, 0);
        Set<Integer> expected = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        assertEquals(expected, numbersInRow);

        numbersInRow = FieldUtilities.getMissingNumbersInRow(VALID_SAMPLE, 7);
        expected = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(expected, numbersInRow);
    }

    @Test
    public void getMissingNumbersInColumn() {
        Set<Integer> numbersInRow = FieldUtilities.getMissingNumbersInColumn(VALID_SAMPLE, 0);
        Set<Integer> expected = new HashSet<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(expected, numbersInRow);

        numbersInRow = FieldUtilities.getMissingNumbersInColumn(VALID_SAMPLE, 7);
        expected = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(expected, numbersInRow);
    }

    @Test
    public void getMissingNumbersInBlock() {
        Set<Integer> numbersInRow = FieldUtilities.getMissingNumbersInBlock(VALID_SAMPLE, 0, 0);
        Set<Integer> expected = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8, 9));
        assertEquals(expected, numbersInRow);

        numbersInRow = FieldUtilities.getMissingNumbersInBlock(VALID_SAMPLE, 3, 0);
        expected = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(expected, numbersInRow);
    }

    @Test
    public void writePossibleNumberInCell() {
    }

    @Test
    public void getPossibilitiesForField() {
    }

    @Test
    public void getPossibilitiesForField1() {
    }

    @Test
    public void isNumberPossibleInField() {
    }

    @Test
    public void checkForValidSudoku() {
        FieldUtilities.checkForValidSudoku(VALID_SAMPLE);
    }

    @Test(expected = DoubledNumberException.class)
    public void checkForInvalidSudoku() {
        FieldUtilities.checkForValidSudoku(INVALID_SAMPLE);
    }

    @Test
    public void getEmptyField() {
        int[][] field = FieldUtilities.getEmptyField();
        assertEquals(field.length, 9);
        assertEquals(field[0].length, 9);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                assertEquals(field[x][y], -1);
            }
        }

    }

}
