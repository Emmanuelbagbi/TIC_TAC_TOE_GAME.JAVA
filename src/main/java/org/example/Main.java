package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    static int boardWidth = 600;
    static int boardHeight = 650;

    static JFrame frame = new JFrame("Tic-Tac-Toe");
    static JLabel textLabel = new JLabel();
    static JPanel textPanel = new JPanel();
    static JPanel boardPanel = new JPanel();
    static JPanel scorePanel = new JPanel();

    static JButton[][] board = new JButton[3][3];

    static String playerX = "X";
    static String playerO = "O";
    static String currentPlayer = playerX;

    static boolean gameOver = false;
    static int turns = 0;

    static int scoreX = 0;
    static int scoreO = 0;
    static JLabel scoreLabel = new JLabel();

    public static void main(String[] args) {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        textPanel.add(restartButton, BorderLayout.EAST);

        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        scorePanel.setLayout(new FlowLayout());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        updateScoreLabel();
        scorePanel.add(scoreLabel);
        frame.add(scorePanel, BorderLayout.SOUTH);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }
    }

    private static void checkWinner() {
        // Check rows
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().equals("") &&
                    board[r][0].getText().equals(board[r][1].getText()) &&
                    board[r][1].getText().equals(board[r][2].getText())) {
                setWinner(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }

        // Check columns
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().equals("") &&
                    board[0][c].getText().equals(board[1][c].getText()) &&
                    board[1][c].getText().equals(board[2][c].getText())) {
                setWinner(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }

        // Check diagonals
        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }
        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        // Check for draw
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    private static void setWinner(JButton... tiles) {
        for (JButton tile : tiles) {
            tile.setForeground(Color.green);
            tile.setBackground(Color.gray);
        }
        textLabel.setText(currentPlayer + " is the Winner!");
        if (currentPlayer.equals(playerX)) {
            scoreX++;
        } else {
            scoreO++;
        }
        updateScoreLabel();
        gameOver = true;
    }

    private static void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Draw!");
    }

    private static void updateScoreLabel() {
        scoreLabel.setText("Score - X: " + scoreX + " O: " + scoreO);
    }

    private static void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
        currentPlayer = playerX;
        turns = 0;
        gameOver = false;
        textLabel.setText("Tic-Tac-Toe");
    }
}
