package commands;

import app.CollectionManager;
import collectionObject.Movie;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс команды GroupCounting, предназначенный для группировки элементов коллекции
 * по значению поля oscarsCount и вывода количества элементов в каждой группе.
 */
public class GroupCounting implements Command {

    /**
     * Выполняет команду группировки элементов коллекции по количеству полученных "Оскаров".
     * Выводит количество фильмов в каждой группе.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = app.App.getApp().getCollectionManager();
        Map<Integer, Integer> grouped = new HashMap<>();

        for (Movie movie : collectionManager.getCollection()) {
            if (movie != null) {
                grouped.put(movie.getOscarsCount(), grouped.getOrDefault(movie.getOscarsCount(), 0) + 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : grouped.entrySet()) {
            System.out.println("Количество оскаров: " + entry.getKey() + " фильмов: " + entry.getValue());
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля oscarsCount, вывести количество элементов в каждой группе";
    }
}
