package ru.itsokay.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Timer;

public class HelloController {
    public AnchorPane scenePane;

    @FXML
    protected void onHelloButtonClick() throws InterruptedException {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }
}