package server.commands;

import common.Response;

import server.CollectionManager;
import server.Server;

import java.io.Serializable;

/**
 * Класс команды Clear, предназначенный для очистки коллекции.
 */
public class Clear extends Command {

    /**
     * Выполняет команду очистки коллекции.
     */
    @Override
    public Response execute(String strArg, Serializable objArg) {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        if (collectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция не содержит элементов, которые можно было бы очистить");
        } else {
            collectionManager.clearCollection();
            return new Response("Коллекция успешно очищена");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
