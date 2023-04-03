package com.simple.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StringReverserController {
    @FXML
    private Label outputText;
    @FXML
    private TextField inputBox;

    @FXML
    protected void onReverseButtonClick() {
        String toReverse = inputBox.getText();
        StringBuilder out = new StringBuilder();

        for (int i = toReverse.length()-1; i >= 0; i--) {
            out.append(toReverse.charAt(i));
        }

        outputText.setText(out.toString());
    }
}