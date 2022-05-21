package ru.itsokay.launcher;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Launcher extends Application {
    // Позиция приложения на экране пользователя
    private double xOffset;
    private double yOffset;
    // Разрешение на перемещение экрана
    private boolean moveAccess = false;

    // Экран
    private Stage stage;

    // Конектор JavaScript к Java
    private final JavaConnector javaConnector = new JavaConnector();
    // Конектор Java к JavaScript
    private JSObject javascriptConnector;


    // Запуск приложения
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT); // Отключение фона
        stage.setResizable(false); // Запрет на изменение размера
        URL url = new File("src/main/resources/ru/itsokay/launcher/html/Main.html").toURI().toURL(); // Подключение к HTML файлу

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        // Подключение конекторов Java и JavaScript
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", javaConnector);
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
            }
        });

        Scene scene = new Scene(browser, 1000, 600);

        // Вызывается при нажатии на ЛКМ
        browser.setOnMousePressed(event -> {
            if (event.getY() <= 30) {
                moveAccess = true;
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        // Вызывается при перемещении мыши с нажатой ЛКМ
        browser.setOnMouseDragged(event -> {
            if (moveAccess) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });

        // Вызывается при отжатии на ЛКМ
        browser.setOnMouseReleased(mouseEvent -> {
            if (moveAccess) {
                moveAccess = false;
            }
        });

        browser.setPageFill(Color.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.getIcons().add(new Image("file:src/main/resources/ru/itsokay/launcher/images/ok.png"));
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