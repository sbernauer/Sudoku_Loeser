package utilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldUtilitiesTest {
    @Test
    public void cloneField() {
    }

    @Test
    public void getNumbersInRow() {
    }

    @Test
    public void getNumbersInColumn() {
    }

    @Test
    public void getNumbersInBlock() {
    }

    @Test
    public void getMissingNumbersInRow() {
    }

    @Test
    public void getMissingNumbersInColumn() {
    }

    @Test
    public void getMissingNumbersInBlock() {
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