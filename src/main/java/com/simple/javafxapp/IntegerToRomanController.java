package com.simple.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class IntegerToRomanController {
    @FXML
    private Label outputText;
    @FXML
    private TextField inputBox;
    private final Map<Integer, String> NUMERALS = new HashMap<>() {{
        put(1000, "M");
        put(900, "CM");
        put(500, "D");
        put(400, "CD");
        put(100, "C");
        put(90, "XC");
        put(50, "L");
        put(40, "XL");
        put(10, "X");
        put(9, "IX");
        put(5, "V");
        put(4, "IV");
        put(1, "I");
    }};

    @FXML
    protected void onIntegerToRomanButtonClick() {
        final String PREFIX = "Your output: ";
        int input;
        try {
            input = Integer.parseInt(inputBox.getText());
            if (input <= 0) {
                throw new NumberFormatException("Number is less than or equal to zero.");
            }
        } catch (NumberFormatException e) {
            String errorMessage = String.format("Integers must be less than %d.", Integer.MAX_VALUE);
            try {
                BigInteger invalid = new BigInteger(inputBox.getText());
                if (invalid.compareTo(new BigInteger("0")) < 1) {
                    errorMessage = "Integers must be non-zero and non-negative.";
                }
            } catch (Exception e2) {
                errorMessage = "Integers only please!";
            }
            new Alert(Alert.AlertType.ERROR, errorMessage).showAndWait();
            outputText.setText(PREFIX + "None");
            return;
        }

        if (input > 9999) {
            AtomicBoolean cancel = new AtomicBoolean(false);
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "The roman numeral output may be very long, would you like to continue?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    outputText.setText(PREFIX + "None");
                    cancel.set(true);
                }
            });
            if (cancel.get()) {
                return;
            }
        }

        int intermediate = input;
        StringBuilder romanized = new StringBuilder();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.addAll(NUMERALS.keySet());

        while (!maxHeap.isEmpty()) {
            if (maxHeap.peek() > intermediate) {
                maxHeap.remove();
            }
            else if (maxHeap.peek() == intermediate ) {
                romanized.append(NUMERALS.get(maxHeap.poll()));
                outputText.setText(PREFIX + romanized);
                return;
            }
            else {
                romanized.append(NUMERALS.get(maxHeap.peek()));
                intermediate -= maxHeap.peek();
            }
        }
        outputText.setText(PREFIX + romanized);
    }
}
