package core.imageRecognition;

import utilities.FieldUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static main.Gui.FIELD_SIZE;

public class PythonSnapSudoku implements ImageRegcognicer {

    @Override
    public int[][] regocniceFromImage(String path) throws IOException, NoSudokuInImageFoundException {
        Process p = Runtime.getRuntime().exec("python Bilderkennung_Github/SnapSudoku/sudoku.py " + path);
        BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String sudokuContents = output.readLine();
        output.close();

        if(sudokuContents == null) {
            throw new NoSudokuInImageFoundException();
        }

        int[][] field = FieldUtilities.getEmptyField();

        int index = 0;
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                char c = sudokuContents.charAt(index);
                if ('0' <= c && c <= '9') {
                    field[x][y] = c - '0';
                }
                index++;
            }
        }
        return field;
    }
}
