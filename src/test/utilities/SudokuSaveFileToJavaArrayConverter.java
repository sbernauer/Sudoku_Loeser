package utilities;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static main.Gui.FIELD_SIZE;

public class SudokuSaveFileToJavaArrayConverter {
    public static void main(String[] args) {
        while (true) {
            File file = askUserForSudokuFilePath();
            int[][] field;
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                field = (int[][]) inputStream.readObject();
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            StringBuilder result = new StringBuilder();
            result.append('{');
            for (int x = 0; x < FIELD_SIZE; x++) {
                result.append('{');
                for (int y = 0; y < FIELD_SIZE; y++) {
                    result.append(field[x][y]);
                    result.append(',');
                }
                result.append("},");
            }
            result.deleteCharAt(result.length() - 1);
            result.append('}');
            System.out.println(result.toString());
        }
    }

    private static File askUserForSudokuFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return "Sudoku Files";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".sudoku");
            }
        });

        int state = fileChooser.showOpenDialog(null);
        if (state == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
