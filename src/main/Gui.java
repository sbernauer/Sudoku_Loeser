package main;

import core.DoubledNumberException;
import core.GuiParser;
import core.ParseExeption;
import core.Solver;
import utilities.FieldUtilities;
import utilities.SudokuDigitInputVerifier;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Gui extends JFrame {
    public static final int FIELD_SIZE = 9;
    private static final String SAVE_FILENAME = System.getProperty("user.home") + "/sudokus/sudoku.ser";

    private JTextField[][] sudokuTextFields = new JTextField[FIELD_SIZE][FIELD_SIZE];
    private int[][] originalInput = FieldUtilities.getEmptyField();
    private boolean[][] markedFields = new boolean[FIELD_SIZE][FIELD_SIZE];

    private JPanel bottomPanel;
    private JLabel lbWarning;

    Gui() {
        initLayout();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 800);
        setVisible(true);

        updateTextSize();
    }


    private void initLayout() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JLabel headerLabel = new JLabel("Sudoku-Löser");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        headerPanel.add(headerLabel);
        this.add(headerPanel, BorderLayout.NORTH);

        JPanel sudokuPanel = new JPanel();
        sudokuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sudokuPanel.setLayout(new GridLayout(FIELD_SIZE, FIELD_SIZE));
        initFieldsOfSudokuPanel(sudokuPanel);
        this.add(sudokuPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 1, 0, 5));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 3, 10, 5));
        JButton btLoesen = new JButton("<html><center>Sudoku<br>lösen</center></html>");
        btLoesen.addActionListener(event1 -> solveCompleteSudoku());
        buttonsPanel.add(btLoesen);
        JButton btMarkierteFelderLoesen = new JButton("<html><center>Markierte Felder<br>lösen</center></html>");
        btMarkierteFelderLoesen.addActionListener(event2 -> markierteFelderLoesen());
        buttonsPanel.add(btMarkierteFelderLoesen);
        JButton btDirektLoesbarAnzeigen = new JButton("<html><center>Direkt berechenbare<br>Felder anzeigen</center></html>");
        btDirektLoesbarAnzeigen.addActionListener(event1 -> direktLoesbarAnzeigen());
        buttonsPanel.add(btDirektLoesbarAnzeigen);
        JButton btLeeren = new JButton("<html><center>Sudoku-Feld<br>leeren</center></html>");
        btLeeren.addActionListener(event -> sudokuFeldLeeren());
        buttonsPanel.add(btLeeren);
        JButton btSpeichern = new JButton("<html><center>Sudoku<br>speichern</center></html>");
        btSpeichern.addActionListener(event -> speichern());
        buttonsPanel.add(btSpeichern);
        JButton btLaden = new JButton("<html><center>Sudoku<br>laden</center></html>");
        btLaden.addActionListener(event -> laden());
        buttonsPanel.add(btLaden);
        JButton btbBildLaden = new JButton("<html><center>Sudoku von<br>Bild laden</center></html>");
        btbBildLaden.addActionListener(event -> bildLaden());
        buttonsPanel.add(btbBildLaden);

        bottomPanel.add(buttonsPanel);

        lbWarning = new JLabel("");
        lbWarning.setForeground(Color.RED);
        lbWarning.setHorizontalAlignment(JLabel.CENTER);

        this.add(bottomPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateTextSize();
            }
        });
    }

    private void sudokuFeldLeeren() {
        originalInput = FieldUtilities.getEmptyField();
        updateFields(originalInput);
        markedFields = new boolean[FIELD_SIZE][FIELD_SIZE];
        resetGui();
    }

    private void speichern() {

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

        int state = fileChooser.showOpenDialog(this);
        if (state == JFileChooser.APPROVE_OPTION) {
            try {
                int[][] fields = GuiParser.parse(sudokuTextFields);
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()));
                outputStream.writeObject(fields);
                outputStream.close();
            } catch (IOException | ParseExeption e) {
                e.printStackTrace();
            }
        }
    }

    private void laden() {
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

        int state = fileChooser.showOpenDialog(this);
        if (state == JFileChooser.APPROVE_OPTION) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                originalInput = FieldUtilities.getEmptyField();
                updateFields((int[][]) inputStream.readObject());
                inputStream.close();
                markedFields = new boolean[FIELD_SIZE][FIELD_SIZE];
                resetGui();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void bildLaden() {
        JFileChooser fileChooser = new JFileChooser("/home/sbernauer/Desktop/Folien/Softwareengineering/Sudoku_Loeser/Bilderkennung_Github/SnapSudoku/train");
        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return "Image Files";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".png") || f.getName().toLowerCase().endsWith(".jpg");
            }
        });

        int state = fileChooser.showOpenDialog(this);
        if (state == JFileChooser.APPROVE_OPTION) {
            try {
                Process p = Runtime.getRuntime().exec("python Bilderkennung_Github/SnapSudoku/sudoku.py " + fileChooser.getSelectedFile());
                BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));

                // read the output from the command
                String sudokuContents = output.readLine();
                output.close();

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
                resetGui();
                updateFields(field);
                updateFields(field);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void solveCompleteSudoku() {
        try {
            resetGui();
            originalInput = GuiParser.parse(sudokuTextFields);
            int[][] result = Solver.solveComplete(originalInput, true);
            updateFields(result);
        } catch (ParseExeption | DoubledNumberException e) {
            setAndDisplayErrorMessage(e);
        }
    }

    private void direktLoesbarAnzeigen() {
        try {
            originalInput = GuiParser.parse(sudokuTextFields);
            int[][] solvedOneStep = Solver.solveOneStep(originalInput);
            for (int x = 0; x < FIELD_SIZE; x++) {
                for (int y = 0; y < FIELD_SIZE; y++) {
                    if (solvedOneStep[x][y] != originalInput[x][y]) {
                        sudokuTextFields[x][y].setBackground(Color.GREEN);
                    }
                }
            }
        } catch (ParseExeption | DoubledNumberException e) {
            setAndDisplayErrorMessage(e);
        }
    }

    private void markierteFelderLoesen() {
        try {
            resetGui();
            originalInput = GuiParser.parse(sudokuTextFields);
            int[][] result = Solver.solveComplete(originalInput, false);
            for (int x = 0; x < FIELD_SIZE; x++) {
                for (int y = 0; y < FIELD_SIZE; y++) {
                    if (result[x][y] != originalInput[x][y] && !markedFields[x][y]) {
                        result[x][y] = -1;
                    }
                }
            }
            updateFields(result);
        } catch (ParseExeption | DoubledNumberException e) {
            setAndDisplayErrorMessage(e);
        }
    }

    private void updateFields(int[][] result) {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                //Set color of field
                if (originalInput[x][y] != -1) {
                    sudokuTextFields[x][y].setBackground(Color.LIGHT_GRAY);
                } else if (markedFields[x][y]) {
                    sudokuTextFields[x][y].setBackground(Color.ORANGE);
                } else {
                    sudokuTextFields[x][y].setBackground(Color.WHITE);
                }
                // Set number of field
                if (result[x][y] == -1) {
                    sudokuTextFields[x][y].setText("");
                } else {
                    sudokuTextFields[x][y].setText(String.valueOf(result[x][y]));
                }
            }
        }
    }

    private void initFieldsOfSudokuPanel(JPanel sudokuPanel) {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                JTextField textField = new JTextField();
                textField.setInputVerifier(SudokuDigitInputVerifier.getInstance());
                textField.setHorizontalAlignment(JTextField.CENTER);

                textField.setBorder(new MatteBorder((x % 3 == 0) ? 5 : 1, (y % 3 == 0) ? 5 : 1, (x == 8) ? 5 : 1, (y == 8) ? 5 : 1, Color.BLACK));

                registerListenersForSudokuFields(textField, x, y);

                sudokuTextFields[x][y] = textField;
                sudokuPanel.add(textField);
            }
        }
    }

    private void registerListenersForSudokuFields(JTextField textField, int positionX, int positionY) {
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() % 2 == 0) {

                    markedFields[positionX][positionY] = !markedFields[positionX][positionY];
                    if (markedFields[positionX][positionY]) {
                        textField.setBackground(Color.ORANGE);
                    } else {
                        textField.setBackground(Color.WHITE);
                    }
                }
            }
        });
        textField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {

                if (!SudokuDigitInputVerifier.getInstance().verify(textField)) { // Textfield ist not filled correctly
                    return;
                }

                char c = e.getKeyChar();

                if (c >= '0' && c <= '9' || c == ' ' || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_ENTER) { // Digit or Space entered, jump to next TextField
                    if (positionX == FIELD_SIZE - 1 && positionY == FIELD_SIZE - 1) {
                        return;
                    }
                    sudokuTextFields[positionX + (positionY + 1) / FIELD_SIZE][(positionY + 1) % FIELD_SIZE].requestFocus();
                    sudokuTextFields[positionX + (positionY + 1) / FIELD_SIZE][(positionY + 1) % FIELD_SIZE].setSelectionStart(0);
                    sudokuTextFields[positionX + (positionY + 1) / FIELD_SIZE][(positionY + 1) % FIELD_SIZE].setSelectionEnd(1);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    JTextField field;
                    if (positionY == 0) {
                        if (positionX == 0) {
                            return;
                        }
                        field = sudokuTextFields[positionX - 1][FIELD_SIZE - 1];
                    } else {
                        field = sudokuTextFields[positionX][positionY - 1];
                    }
                    field.requestFocus();
                    field.setSelectionStart(0);
                    field.setSelectionEnd(1);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (positionX == 0) {
                        return;
                    }
                    JTextField field = sudokuTextFields[positionX - 1][positionY];
                    field.requestFocus();
                    field.setSelectionStart(0);
                    field.setSelectionEnd(1);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (positionX == FIELD_SIZE - 1) {
                        return;
                    }
                    JTextField field = sudokuTextFields[positionX + 1][positionY];
                    field.requestFocus();
                    field.setSelectionStart(0);
                    field.setSelectionEnd(1);
                }
            }
        });
    }

    private void updateTextSize() {
        int fontSize = (int) Math.min(getHeight() / FIELD_SIZE / 1.5, getWidth() / FIELD_SIZE / 1.5);
        fontSize = Math.max(fontSize, 15);
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                sudokuTextFields[x][y].setFont(new Font("Arial", Font.PLAIN, fontSize));
            }
        }
    }

    private void setAndDisplayErrorMessage(Exception e) {
        bottomPanel.add(lbWarning);
        lbWarning.setText(e.getMessage());
        if (e instanceof DoubledNumberException) {
            int row = ((DoubledNumberException) e).getRow();
            int column = ((DoubledNumberException) e).getColumn();
            sudokuTextFields[row][column].setBackground(Color.RED);
        }
        revalidate();
        repaint();
    }

    private void resetGui() {
        if (bottomPanel.getComponents().length == 2) {
            bottomPanel.remove(bottomPanel.getComponents()[1]);
        }
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                sudokuTextFields[x][y].setBackground(Color.WHITE);
                if (markedFields[x][y]) {
                    sudokuTextFields[x][y].setBackground(Color.ORANGE);
                }
            }
        }
        revalidate();
        repaint();
    }
}
