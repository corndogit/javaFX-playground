package com.simple.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class GridThingController {
    @FXML
    private int[][] theNumbers = new int[3][3];
    @FXML
    private GridPane gridThing;

    protected void setTheNumbersAllRandomly() {
        Random random = new Random();
        for (int y = 0; y < theNumbers.length; y++) {
            for (int x = 0; x < theNumbers.length; x++) {
                theNumbers[y][x] = random.nextInt();
            }
        }
    }
}
