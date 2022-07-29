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
import java.net.MalformedURLException;
import java.net.URL;

public class Launcher extends Application {
    // Позиция приложения на экране пользователя
    private double xOffset;
    private double yOffset;
    // Разрешение на перемещение экрана
    protected boolean moveAccess = false;

    // Экран
    protected Stage stage;

    // Конектор JavaScript к Java
    protected final JavaConnector javaConnector = new JavaConnector(this);
    // Конектор Java к JavaScript
    protected JSObject javascriptConnector;
    boolean ctrl = false;
    boolean shift = false;
    boolean alt = false;
    Checks checks;

    protected URL url = new File("src/main/resources/ru/itsokay/launcher/html/Main.html").toURI().toURL(); // Подключение к HTML файлу

    public Launcher() throws MalformedURLException {
    }


    // Запуск приложения
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(new File(".").getCanonicalPath()); // Путь, где запущено приложение.
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT); // Отключение фона
        stage.setResizable(false); // Запрет на изменение размера
        final WebView browser = new WebView(); // Браузер
        final WebEngine webEngine = browser.getEngine(); // Веб-ядро

        // Подключение конекторов Java и JavaScript

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", javaConnector);
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
//                checks = new Checks((JSObject) webEngine.executeScript("getChecksJsConnector()"), javaConnector);
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

        // При нажатии Ctrl+W окно будет закрыто с анимацией закрытия
        browser.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case CONTROL -> this.ctrl = true;
                case W -> {
                    if (this.ctrl)
                        webEngine.executeScript("closeAnimation()");
                }
            }
        });

        // Отключает нажатие CTRL и SHIFT если их отпустить
        browser.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case CONTROL -> ctrl = false;
                case SHIFT -> this.shift = false;
            }
        });

        // Перехват внепланового закрытия окна через панель задач или Alt+F4 и даже через диспетчер задач
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            webEngine.executeScript("closeAnimation()");
        });

        browser.setPageFill(Color.TRANSPARENT); // Отключение фона, чтобы наложить поверх HTML
        scene.setFill(Color.TRANSPARENT); // Отключение фона, чтобы наложить поверх HTML
        stage.getIcons().add(new Image("file:src/main/resources/ru/itsokay/launcher/images/ok.png")); // Иконка приложения
        stage.setTitle("Hello!"); // Название приложения
        stage.setScene(scene); // хз
        stage.show(); // Что-то показывает
        webEngine.load(url.toString()); // Подгружает главный HTML файл
        // Исполняет скрипты выбора менюшки и сервера
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                webEngine.executeScript("loadPage();");
                webEngine.executeScript("SelectSelectedServer();");
            }
        });


//        if (!checks.checkInternetConnection()) {
//            System.out.println("CONNECTION ERROR");
//        } else {
//            System.out.println("Ok.");
//        }
    }

    public static void main(String[] args) {
        launch();
    }
}