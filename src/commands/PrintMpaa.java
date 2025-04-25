package commands;

import app.App;
import app.CollectionManager;

import java.util.HashSet;

/**
 * Класс команды PrintMpaa, предназначенный для вывода уникальных значений поля mpaaRating всех элементов в коллекции.
 */
public class PrintMpaa implements Command {

    /**
     * Выполняет команду, выводя уникальные значения поля mpaaRating из коллекции.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        HashSet<String> set = collectionManager.uniqueMpaa();
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция пуста, невозможно вывести уникальные mpaaRating");
        } else {
            for (String mpaa : set) {
                System.out.println(mpaa);
            }
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "вывести уникальные значения поля mpaaRating всех элементов в коллекции";
    }
}