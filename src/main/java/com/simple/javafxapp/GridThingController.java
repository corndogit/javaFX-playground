package com.simple.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class GridThingController {
    // Grid for tracking moves and checking wins
    protected int[][] buttons = new int[3][3];  // 1 = player 1, -1 = player 2, 0 = empty

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

        // update buttons matrix and check for win
        int[] colAndRow = idToIndices.get(target.getId());
        buttons[colAndRow[0]][colAndRow[1]] = player1Turn ? 1 : -1;
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

        // reflect changes in UI
        target.setText(marking);
        marking = player1Turn ? "O" : "X";  // if player 1 made a move, change to player 2's marking
        player1Turn = !player1Turn;
        turnIndicator.setText(String.format(LABEL_TEMPLATE, player1Turn ? 1 : 2, marking));
    }

    protected String checkGameStatus(int row, int col) {
        return "pending";  // todo: implement win checking algorithm, adapting from Python Leetcode
    }

    @FXML
    protected void resetGame() {
        for (Node target : gridThing.getChildren()) {
            if (target instanceof Button) {
                ((Button) target).setText("");
            }
        }
        player1Turn = true;
        marking = "X";
        turnIndicator.setText(String.format(LABEL_TEMPLATE, 1, marking));
    }
}
