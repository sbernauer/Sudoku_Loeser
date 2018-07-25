package core.persistance;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;

public class SudokuFieldSaveFile {

    public static boolean saveToFile(int[][] fields) throws IOException {
        File file = askUserForSudokuFilePath();

        if (file != null) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(fields);
            outputStream.close();
            return true;
        }
        return false;
    }

    public static int[][] loadFromFile() throws IOException, ClassNotFoundException {
        File file = askUserForSudokuFilePath();

        if (file != null) {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            int[][] result = (int[][]) inputStream.readObject();
            inputStream.close();
            return result;
        }
        return null;
    }

    /**
     * @return the file selected by the user or null if the user has canceled the selection
     */
    public static File askUserForSudokuFilePath() {
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
