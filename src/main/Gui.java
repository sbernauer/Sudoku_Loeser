package main;

import core.GuiParser;
import core.ParseExeption;
import core.Solver;
import utilities.FieldUtilities;
import utilities.SudokuDigitInputVerifier;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Gui extends JFrame {
    public static final int FIELD_SIZE = 9;
    private static final String SAVE_FILENAME = System.getProperty("user.home") + "/sudokus/sudoku.ser";

    private JTextField[][] sudokuTextFields = new JTextField[FIELD_SIZE][FIELD_SIZE];
    private int[][] originalInput = FieldUtilities.getEmptyField();

    public Gui() {

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

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 2, 10, 5));
        JButton btLoesen = new JButton("Sudoku lösen");
        btLoesen.addActionListener(this::solveCompleteSudoku);
        bottomPanel.add(btLoesen);
        bottomPanel.add(new JButton("Markierte Felder lösen"));
        bottomPanel.add(new JButton("Direkt berechenbare Felder anzeigen"));

        JButton btLeeren = new JButton("Sudoku-Feld leeren");
        btLeeren.addActionListener(this::sudokuFeldLeeren);
        bottomPanel.add(btLeeren);

        JButton btSpeichern = new JButton("Sudoku speichern");
        btSpeichern.addActionListener(this::speichern);
        bottomPanel.add(btSpeichern);

        JButton btLaden = new JButton("Sudoku laden");
        btLaden.addActionListener(this::laden);
        bottomPanel.add(btLaden);

        this.add(bottomPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateTextSize();
            }
        });
    }

    private void sudokuFeldLeeren(ActionEvent actionEvent) {
        originalInput = FieldUtilities.getEmptyField();
        updateFields(originalInput);
    }

    private void speichern(ActionEvent actionEvent) {
        try {
            int[][] fields = GuiParser.parse(sudokuTextFields);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILENAME));
            outputStream.writeObject(fields);
            outputStream.close();
        } catch (IOException | ParseExeption e) {
            e.printStackTrace();
        }
    }

    private void laden(ActionEvent actionEvent) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILENAME));
            originalInput = FieldUtilities.getEmptyField();
            updateFields((int[][]) inputStream.readObject());
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void solveCompleteSudoku(ActionEvent actionEvent) {
        try {
            originalInput = GuiParser.parse(sudokuTextFields);
            int[][] result = Solver.solveComplete(originalInput);
            updateFields(result);
        } catch (ParseExeption parseExeption) {
            parseExeption.printStackTrace();
        }
    }

    private void updateFields(int[][] result) {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                //Set color of field
                if (originalInput[x][y] != -1) {
                    sudokuTextFields[x][y].setBackground(Color.LIGHT_GRAY);
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
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                int fontSize = (int) Math.min(getHeight() / FIELD_SIZE / 1.5, getWidth() / FIELD_SIZE / 1.5);
                fontSize = Math.max(fontSize, 15);
                sudokuTextFields[x][y].setFont(new Font("Arial", Font.PLAIN, fontSize));
            }
        }
    }

}
