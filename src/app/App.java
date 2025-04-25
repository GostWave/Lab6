package app;

import IO.FileManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Основной класс приложения, управляющий его компонентами и выполняющий команды.
 * Реализует паттерн Singleton для обеспечения единственного экземпляра.
 */
public class App {
    private CollectionManager collectionManager;
    private ConsoleCaller consoleCaller;
    private FileManager fileManager;
    private CommandManager commandManager;
    private static App app;
    Scanner scanner = new Scanner(System.in);

    /**
     * Приватный конструктор для предотвращения создания экземпляров класса извне.
     */
    private App() {
    }

    /**
     * Возвращает единственный экземпляр класса App.
     * Если он ещё не создан, создаёт новый.
     *
     * @return экземпляр класса App
     */
    public static App getApp() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    /**
     * Устанавливает менеджер коллекции.
     *
     * @param collectionManager объект CollectionManager
     */
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает менеджер коллекции.
     *
     * @return объект CollectionManager
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Возвращает менеджер команд.
     *
     * @return объект CommandManager
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Устанавливает менеджер команд.
     *
     * @param commandManager объект CommandManager
     */
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Устанавливает менеджер файлов.
     *
     * @param fileManager объект FileManager
     */
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Возвращает менеджер файлов.
     *
     * @return объект FileManager
     */
    public FileManager getFileManager() {
        return fileManager;
    }

    /**
     * Устанавливает консольный обработчик команд.
     *
     * @param consoleCaller объект ConsoleCaller
     */
    public void setConsoleCaller(ConsoleCaller consoleCaller) {
        this.consoleCaller = consoleCaller;
    }

    /**
     * Возвращает консольный обработчик команд.
     *
     * @return объект ConsoleCaller
     */
    public ConsoleCaller getConsoleCaller() {
        return consoleCaller;
    }

    /**
     * Запускает приложение, ожидая ввода команд от пользователя.
     * В случае ввода "exit" приложение завершает работу.
     */
    public void start() {
        while (true) {
            try {
                String text = scanner.nextLine().toLowerCase().trim();
                if (text.equals("exit")) {
                    break;
                }
                consoleCaller.call(text);
            } catch (NoSuchElementException e){
                System.out.println("Ctrl + D. Программа будет завершена.");
                break;
            }

        }
    }
}
