package server.commands;

import common.Response;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;

/**
 * Класс команды Shuffle, предназначенный для перемешивания элементов коллекции в случайном порядке.
 */
public class Shuffle extends Command {

    /**
     * Выполняет команду перемешивания элементов коллекции.
     */
    @Override
    public Response execute(String strArg, Serializable objArg) {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        if (!collectionManager.getCollection().isEmpty()) {
            collectionManager.shuffleCollection();
            return new Response("Коллекция успешно перемешана");
        } else {
            return new Response("Коллекция пуста, её нельзя перемешать");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
    }
}