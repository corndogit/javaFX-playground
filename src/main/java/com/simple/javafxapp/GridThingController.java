package com.simple.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class GridThingController {
    // Grid for tracking moves and checking wins
    protected int[][] board = new int[3][3];  // 1 = player 1, -1 = player 2, 0 = empty

    protected Map<String, int[]> idToIndices = Map.ofEntries(
            Map.entry("item1", new int[]{0, 0}),  // map button ids to arrays of row/column index in buttons matrix
            Map.entry("item2", new int[]{0, 1}),
            Map.entry("item3", new int[]{0, 2}),
            Map.entry("item4", new int[]{1, 0}),
            Map.entry("item5", new int[]{1, 1}),
            Map.entry("item6", new int[]{1, 2}),
            Map.entry("item7", new int[]{2, 0}),
            Map.entry("item8", new int[]{2, 1}),
            Map.entry("item9", new int[]{2, 2})
    );

    @FXML
    protected GridPane gridThing;
    @FXML
    protected Label turnIndicator;

    protected boolean player1Turn = true;
    protected boolean gameIsEnded = false;
    protected int turnCount = 0;
    protected String marking = "X";
    protected final String LABEL_TEMPLATE = "Player %d's turn (uses %s)";

    @FXML
    protected void makeMove(MouseEvent event) {
        // do nothing if game has been won
        if (gameIsEnded) {
            return;
        }

        // check if valid move
        Button target = (Button) event.getSource();
        if (!target.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid move, try again.");
            alert.showAndWait();
            return;
        }

        // reflect changes on the board
        target.setText(marking);
        marking = player1Turn ? "O" : "X";  // if player 1 made a move, change to player 2's marking

        // update buttons matrix and check for win
        int[] colAndRow = idToIndices.get(target.getId());
        board[colAndRow[0]][colAndRow[1]] = player1Turn ? 1 : -1;
        String status = checkGameStatus(colAndRow[0], colAndRow[1]);
        if ("p1 win".equals(status)) {
            turnIndicator.setText("Player 1 wins!");
            gameIsEnded = true;
            return;
        }
        if ("p2 win".equals(status)) {
            turnIndicator.setText("Player 2 wins!");
            gameIsEnded = true;
            return;
        }
        if ("draw".equals(status)) {
            turnIndicator.setText("It's a draw!");
            gameIsEnded = true;
            return;
        }

        // change player
        player1Turn = !player1Turn;
        turnCount++;
        turnIndicator.setText(String.format(LABEL_TEMPLATE, player1Turn ? 1 : 2, marking));
    }

    /**
     * Checks the game board for a win along the rows, columns or diagonals. If the sum of a row, column or diagonal
     * adds up to 3 (all player 1) or -3 (all player 2), then a player has won.
     * @param row row index of the new move
     * @param col column index of the new move
     * @return Status of the game out of the following: "p1 win", "p2 win", "draw", or "pending"
     */
    protected String checkGameStatus(int row, int col) {
        int rowSum = board[row][0] + board[row][1] + board[row][2];
        int colSum = board[0][col] + board[1][col] + board[2][col];
        int diagonalTopLeftToBottomRight = board[0][0] + board[1][1] + board[2][2];
        int diagonalBottomLeftToTopRight = board[2][0] + board[1][1] + board[0][2];
        System.out.printf("rowSum: %d%ncolSum: %d%n diag1: %d%ndiag2: %d%n", rowSum, colSum, diagonalTopLeftToBottomRight, diagonalBottomLeftToTopRight);
        if (rowSum == 3 || rowSum == -3) {
            return rowSum == 3 ? "p1 win" : "p2 win";
        }
        if ((colSum == 3) || (colSum == -3)) {
            return colSum == 3 ? "p1 win" : "p2 win";
        }
        if ((diagonalBottomLeftToTopRight == 3) || (diagonalBottomLeftToTopRight == -3)) {
            return diagonalBottomLeftToTopRight == 3 ? "p1 win" : "p2 win";
        }
        if ((diagonalTopLeftToBottomRight == 3) || (diagonalTopLeftToBottomRight == -3)) {
            return diagonalTopLeftToBottomRight == 3 ? "p1 win" : "p2 win";
        }
        if (turnCount >= 9) {
            return "draw";
        }
        return "pending";
    }

    @FXML
    protected void resetGame() {
        for (Node target : gridThing.getChildren()) {
            if (target instanceof Button) {
                ((Button) target).setText("");
            }
        }
        player1Turn = true;
        gameIsEnded = false;
        marking = "X";
        turnCount = 0;
        board = new int[3][3];
        turnIndicator.setText(String.format(LABEL_TEMPLATE, 1, marking));
    }
}
