package com.simple.javafxapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public VBox main;

    @FXML
    protected void playGridThingGame() throws IOException {
        FXMLLoader gameView = new FXMLLoader(MainController.class.getResource("grid-thing-view.fxml"));
        Scene gameScene = new Scene(gameView.load(), 600, 600);
        Stage stage = new Stage();
        stage.setTitle("Grid Thing");
        stage.setScene(gameScene);
        stage.show();
    }
}
