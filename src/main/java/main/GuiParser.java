package main;

import core.exceptions.ParseExeption;
import utilities.FieldUtilities;
import utilities.SudokuDigitInputVerifier;

import javax.swing.*;

import static main.Gui.FIELD_SIZE;

public class GuiParser {

    public static int[][] parse(JTextField[][] sudokuFields) throws IllegalArgumentException, ParseExeption {
        if (sudokuFields.length != FIELD_SIZE || sudokuFields[0].length != FIELD_SIZE) {
            throw new IllegalArgumentException("The given sudokuFelds-Array had the wrong dimensions of " + sudokuFields.length + ", " + sudokuFields[0].length);
        }

        int[][] result = new int[FIELD_SIZE][FIELD_SIZE];

        SudokuDigitInputVerifier sudokuDigitInputVerifier = SudokuDigitInputVerifier.getInstance();

        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                JTextField textField = sudokuFields[x][y];
                if (textField == null) {
                    throw new IllegalArgumentException("The given JtextField from the sudokuFelds was null");
                }
                if (!sudokuDigitInputVerifier.verify(textField)) {
                    throw new ParseExeption("The given JtextField at position " + x + ", " + y + " had the invalid text " + textField.getText());
                }
                String text = textField.getText();
                switch (text) {
                    case "":
                    case " ":
                        result[x][y] = -1;
                        break;
                    default:
                        result[x][y] = Integer.parseInt(text);
                }
            }
        }

        FieldUtilities.checkForValidSudoku(result);

        return result;
    }
}
