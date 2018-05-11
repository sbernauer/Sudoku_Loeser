package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldUtilitiesTest {
    @Test
    void cloneField() {
    }

    @Test
    void getNumbersInRow() {
    }

    @Test
    void getNumbersInColumn() {
    }

    @Test
    void getNumbersInBlock() {
    }

    @Test
    void getMissingNumbersInRow() {
    }

    @Test
    void getMissingNumbersInColumn() {
    }

    @Test
    void getMissingNumbersInBlock() {
    }

    @Test
    void writePossibleNumberInCell() {
    }

    @Test
    void getPossibilitiesForField() {
    }

    @Test
    void getPossibilitiesForField1() {
    }

    @Test
    void isNumberPossibleInField() {
    }

    @Test
    void checkForValidSudoku() {
    }

    @Test
    void getEmptyField() {
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