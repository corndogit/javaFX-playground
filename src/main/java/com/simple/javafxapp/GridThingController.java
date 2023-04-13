package com.simple.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class GridThingController {
    // Buttons
    @FXML
    private Button item1;
    @FXML
    private Button item2;
    @FXML
    private Button item3;
    @FXML
    private Button item4;
    @FXML
    private Button item5;
    @FXML
    private Button item6;
    @FXML
    private Button item7;
    @FXML
    private Button item8;
    @FXML
    private Button item9;
    protected Button[][] buttons = new Button[][]{
            {item1, item2, item3},
            {item4, item5, item6},
            {item7, item8, item9}};

    // Grid
    @FXML
    private int[][] theNumbers = new int[3][3];
    @FXML
    private GridPane gridThing;
    static Random random = new Random();

    @FXML
    protected void setAllButtonNumbers() {
        for (int y = 0; y < theNumbers.length; y++) {
            for (int x = 0; x < theNumbers.length; x++) {
                theNumbers[y][x] = random.nextInt(0, 9);
                buttons[y][x].setText(String.valueOf(theNumbers[y][x]));
            }
        }
    }

    @FXML
    private void setButtonNumber(MouseEvent event) {
        String out = event.getSource().toString();
        Alert test = new Alert(Alert.AlertType.INFORMATION, out);
        test.showAndWait();
    }
}
