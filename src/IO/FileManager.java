package IO;

import app.App;
import app.CollectionManager;
import app.CommandManager;
import app.ConsoleCaller;
import collectionObject.Movies;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Scanner;


/**
 * Класс FileManager предназначен для управления загрузкой и сохранением коллекции в файл.
 */
public class FileManager {

    /**
     * Название файла, получаемое из переменной окружения COLLECTION_FILE.
     */
    private final String fileName = System.getenv("COLLECTION_FILE");

    /**
     * Менеджеры коллекции и команд .
     */
    private final CollectionManager collectionManager = App.getApp().getCollectionManager();
    private final CommandManager commandManager = App.getApp().getCommandManager();
    private final ConsoleCaller consoleCaller = App.getApp().getConsoleCaller();
    /**
     * Строковое представление данных файла.
     */
    private String text;

    /**
     * Считывает коллекцию из файла.
     */
    public void importCollection() {
        File file = null;
        try {
            file = new File(fileName);
        } catch (NullPointerException e) {
            System.out.println("Не передано имя файла. Программа будет завершена");
            System.exit(0);
        }

        if (file != null) {
            if (file.exists()) {
                if (!file.canRead()){
                    System.out.println("Ошибка: Нет прав доступа к файлу. Программа будет завершена");
                    System.exit(0);
                } else {
                    fileReader(file);
                    textToObject();
                }
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("Неверное имя файла. Программа будет завершена");
                    System.exit(0);
                }
            }
        }
    }



    /**
     * Читает данные из файла и сохраняет их в переменной text.
     *
     * @param file файл, из которого считываются данные
     */
    public void fileReader(File file) {
        try {
            Scanner scanner = new Scanner(file, "UTF-8");
            scanner.useDelimiter("\\z");
            if (scanner.hasNext()) {
                text = scanner.next();
            }
        } catch (FileNotFoundException e){
                System.out.println("Ошибка: отсутствует файл");
            }
        }


    /**
     * Преобразует текстовые данные в объект коллекции и загружает их в менеджер коллекции.
     */
    public void textToObject() {
        if (text != null) {

            JAXBWorker jaxbWorker = new JAXBWorker();
            Movies movies = jaxbWorker.convertXmlToObject(text);
            if ((movies != null) && (movies.getMovies() != null)) {
                CollectionChecker collectionChecker = new CollectionChecker(movies);
                if (collectionChecker.checkCollection()) {
                    collectionManager.setCollection(movies);
                    System.out.println("Коллекция успешно импортирована из файла");
                }

            }
        }
    }

    /**
     * Записывает текущую коллекцию в файл.
     */
    public void fileWriter() {
        JAXBWorker jaxbWorker = new JAXBWorker();
        File file = new File("./" + fileName);
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        Movies movies = new Movies();
        movies.setMovies(collectionManager.getCollection());

        if (movies.getMovies().isEmpty()) {
            if (file.exists()) {
                try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
                    writer.write("");
                    System.out.println("Коллекция пуста, файл очищен");
                } catch (IOException e) {
                    System.out.println("Не удалось очистить файл");
                }
            } else {
                System.out.println("Коллекция пуста");
            }
        } else {
            try {
                jaxbWorker.convertObjectToXml(movies);
            } catch (JAXBException e) {
                System.out.println("Не удалось записать коллекцию в файл");
            }
        }
    }
}