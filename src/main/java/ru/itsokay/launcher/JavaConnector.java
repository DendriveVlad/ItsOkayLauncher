package ru.itsokay.launcher;

import java.io.File;
import java.io.IOException;

public class JavaConnector {
    Launcher mainClass;

    public JavaConnector(Launcher cls) {
        mainClass = cls;
    }

    public void test(String a) {
        System.out.println(a);
    }

    public void blockMove() {
        mainClass.moveAccess = false;
    }  // Запрет движения окна при нажатии на кнопки

    public void close() {
        mainClass.stage.close();
    }  // Кнопка закрытия

    public void minimize() {
        mainClass.stage.setIconified(true);
    }  // Кнопка сворачивания

    public void play() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("C:/Users/dendr/AppData/Roaming/.minecraft/versions/1.19", "-jar", "1.19.jar");
        pb.directory(new File("preferred/working/directory"));
        Process p = pb.start();
    }
}
