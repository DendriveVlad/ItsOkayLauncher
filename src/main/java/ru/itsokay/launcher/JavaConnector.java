package ru.itsokay.launcher;

import java.io.*;

public class JavaConnector {
    Launcher mainClass;

    public JavaConnector(Launcher cls) {
        mainClass = cls;
    }

    // Вывод сообщений в консоль из жс
    public void test(String a) {
        System.out.println(a);
    }

    // Блокировка движения окна при нажатии на кнопки
    public void blockMove() {
        mainClass.moveAccess = false;
    }  // Запрет движения окна при нажатии на кнопки

    // закрытие приложения
    public void close() {
        mainClass.stage.close();
    }  // Кнопка закрытия

    // свёртывание приложения
    public void minimize() {
        mainClass.stage.setIconified(true);
    }  // Кнопка сворачивания

    // запуск игры? Не работает!
    public void play() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("C:/Users/dendr/AppData/Roaming/.minecraft/versions/1.19", "-jar", "1.19.jar");
        pb.directory(new File("preferred/working/directory"));
        Process p = pb.start();
    }

    // Подгрузка (Чтение) HTML-файла и отправка его в жс
    public String loadPage(String page) throws FileNotFoundException {
        StringBuilder html = new StringBuilder();
        FileReader fr = new FileReader("src/main/resources/ru/itsokay/launcher/html/pages/" + page + ".html");
        try {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }
            br.close();
            return html.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
