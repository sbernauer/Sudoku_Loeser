package core.solveStrategies;

import utilities.FieldUtilities;

import java.util.HashMap;
import java.util.Map;

public class MissingThirdNumberStrategy implements SolveStrategy {

    @Override
    public int[][] applyTo(int[][] field) {
        for (int searchingNumber = 1; searchingNumber < 10; searchingNumber++) {
            searchInVerticalStripe(field, 0, searchingNumber);
            searchInVerticalStripe(field, 3, searchingNumber);
            searchInVerticalStripe(field, 6, searchingNumber);
            searchInHorizontalStripe(field, 0, searchingNumber);
            searchInHorizontalStripe(field, 3, searchingNumber);
            searchInHorizontalStripe(field, 6, searchingNumber);
        }
        return field;
    }

    /**
     * Does a search at the given 3er-Stripe vertical
     */
    private void searchInVerticalStripe(int[][] field, int startY, int searchingNumber) {
        Map<Integer, Boolean> includes = new HashMap<>();

        for (int startX = 0; startX < 9; startX += 3) {
            includes.put(startX, FieldUtilities.getNumbersInBlock(field, startX, startY, false).contains(searchingNumber));
        }

        long sumPerStripe = includes.values().stream().filter(Boolean::booleanValue).count();
        if (sumPerStripe != 2) {
            return;
        }

        int startXOfBlockWithMissingNumber;
        if (!includes.get(0)) {
            startXOfBlockWithMissingNumber = 0;
        } else if (!includes.get(3)) {
            startXOfBlockWithMissingNumber = 3;
        } else if (!includes.get(6)) {
            startXOfBlockWithMissingNumber = 6;
        } else {
            return;
        }

        int indexYOfColumnWithMissingNumber;
        if (!FieldUtilities.getNumbersInColumn(field, startY, false).contains(searchingNumber)) {
            indexYOfColumnWithMissingNumber = startY;
        } else if (!FieldUtilities.getNumbersInColumn(field, startY + 1, false).contains(searchingNumber)) {
            indexYOfColumnWithMissingNumber = startY + 1;
        } else if (!FieldUtilities.getNumbersInColumn(field, startY + 2, false).contains(searchingNumber)) {
            indexYOfColumnWithMissingNumber = startY + 2;
        } else {
            return;
        }

        replaceInVerticalStripe(field, startXOfBlockWithMissingNumber, indexYOfColumnWithMissingNumber, searchingNumber);
    }

    private void replaceInVerticalStripe(int[][] field, int startX, int startY, int searchingNumber) {
        boolean field1 = field[startX + 0][startY] == -1 && FieldUtilities.isNumberPossibleInField(field, startX + 0, startY, searchingNumber);
        boolean field2 = field[startX + 1][startY] == -1 && FieldUtilities.isNumberPossibleInField(field, startX + 1, startY, searchingNumber);
        boolean field3 = field[startX + 2][startY] == -1 && FieldUtilities.isNumberPossibleInField(field, startX + 2, startY, searchingNumber);

        if (field1 && !field2 && !field3) {
            field[startX][startY] = searchingNumber;
        } else if (!field1 && field2 && !field3) {
            field[startX + 1][startY] = searchingNumber;
        } else if (!field1 && !field2 && field3) {
            field[startX + 2][startY] = searchingNumber;
        }
    }

    /**
     * Does a search at the given 3er-Stripe horizontal
     */
    private void searchInHorizontalStripe(int[][] field, int startX, int searchingNumber) {
        Map<Integer, Boolean> includes = new HashMap<>();

        for (int startY = 0; startY < 9; startY += 3) {
            includes.put(startY, FieldUtilities.getNumbersInBlock(field, startX, startY, false).contains(searchingNumber));
        }

        long sumPerStripe = includes.values().stream().filter(Boolean::booleanValue).count();
        if (sumPerStripe != 2) {
            return;
        }

        int startYOfBlockWithMissingNumber;
        if (!includes.get(0)) {
            startYOfBlockWithMissingNumber = 0;
        } else if (!includes.get(3)) {
            startYOfBlockWithMissingNumber = 3;
        } else if (!includes.get(6)) {
            startYOfBlockWithMissingNumber = 6;
        } else {
            return;
        }

        int indexXOfRowWithMissingNumber;
        if (!FieldUtilities.getNumbersInRow(field, startX, false).contains(searchingNumber)) {
            indexXOfRowWithMissingNumber = startX;
        } else if (!FieldUtilities.getNumbersInRow(field, startX + 1, false).contains(searchingNumber)) {
            indexXOfRowWithMissingNumber = startX + 1;
        } else if (!FieldUtilities.getNumbersInRow(field, startX + 2, false).contains(searchingNumber)) {
            indexXOfRowWithMissingNumber = startX + 2;
        } else {
            return;
        }

        replaceInHorizontalStripe(field, indexXOfRowWithMissingNumber, startYOfBlockWithMissingNumber, searchingNumber);
    }

    private void replaceInHorizontalStripe(int[][] field, int startX, int startY, int searchingNumber) {
        boolean field1 = field[startX][startY + 0] == -1 && FieldUtilities.isNumberPossibleInField(field, startX, startY + 0, searchingNumber);
        boolean field2 = field[startX][startY + 1] == -1 && FieldUtilities.isNumberPossibleInField(field, startX, startY + 1, searchingNumber);
        boolean field3 = field[startX][startY + 2] == -1 && FieldUtilities.isNumberPossibleInField(field, startX, startY + 2, searchingNumber);

        if (field1 && !field2 && !field3) {
            field[startX][startY] = searchingNumber;
        } else if (!field1 && field2 && !field3) {
            field[startX][startY + 1] = searchingNumber;
        } else if (!field1 && !field2 && field3) {
            field[startX][startY + 2] = searchingNumber;
        }
    }
}
