package ru.itsokay.launcher;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    private double xOffset;
    private double yOffset;
    private boolean moveAccess = false;
    private Stage stage;
    private final JavaConnector javaConnector = new JavaConnector();
    private JSObject javascriptConnector;


    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        URL url = new File("src/main/resources/ru/itsokay/launcher/test.html").toURI().toURL();

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();


        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", javaConnector);
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
            }
        });

        Scene scene = new Scene(browser, 1280, 720);

        browser.setOnMousePressed(event -> {
            if (event.getY() <= 30) {
                moveAccess = true;
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        browser.setOnMouseDragged(event -> {
            if (moveAccess) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        browser.setOnMouseReleased(mouseEvent -> {
            if (moveAccess) {
                moveAccess = false;
            }
        });

        browser.setPageFill(Color.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        webEngine.load(url.toString());
    }

    public static void main(String[] args) {
        launch();
    }

    public class JavaConnector {
        public void close() {
            stage.close();
        }

        public void minimize() {
            stage.setIconified(true);
        }
    }
}