package server.commands;

import common.Response;
import common.collectionObject.Movie;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс команды GroupCounting, предназначенный для группировки элементов коллекции
 * по значению поля oscarsCount и вывода количества элементов в каждой группе.
 */
public class GroupCounting extends Command {

    /**
     * Выполняет команду группировки элементов коллекции по количеству полученных "Оскаров".
     * Выводит количество фильмов в каждой группе.
     */
    @Override
    public Response execute(String strArg, Serializable objArg) {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        Map<Integer, Integer> grouped = new HashMap<>();
        StringBuilder out = new StringBuilder();
        for (Movie movie : collectionManager.getCollection()) {
            if (movie != null) {
                grouped.put(movie.getOscarsCount(), grouped.getOrDefault(movie.getOscarsCount(), 0) + 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : grouped.entrySet()) {
            out.append("Количество оскаров: " + entry.getKey() + " фильмов: " + entry.getValue()+"\n");
        }
        return new Response(out.toString());
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
