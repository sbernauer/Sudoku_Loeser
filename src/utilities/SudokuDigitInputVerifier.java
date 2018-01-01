package utilities;

import javax.swing.*;
import java.io.Serializable;

public class SudokuDigitInputVerifier extends InputVerifier implements Serializable {

    private SudokuDigitInputVerifier() {

    }

    private static final String REGEX = "[ 1-9]?";
    private static SudokuDigitInputVerifier instance = null;

    public static SudokuDigitInputVerifier getInstance() {
        if (instance == null) {
            instance = new SudokuDigitInputVerifier();
        }
        return instance;
    }

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        return text.matches(REGEX);
    }
}
