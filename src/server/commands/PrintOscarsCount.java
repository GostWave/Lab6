package server.commands;

import common.Response;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;

/**
 * Класс команды PrintOscarsCount, предназначенный для вывода значений поля oscarsCount
 * всех элементов коллекции в порядке возрастания.
 */
public class PrintOscarsCount extends Command {

    /**
     * Выполняет команду, выводя значения поля oscarsCount всех элементов в коллекции.
     */
    @Override
    public Response execute(String strArg, Serializable objArg) {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        if (collectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пуста, невозможно вывести oscarsCount");
        } else {
            return new Response(collectionManager.printOscarsCount());
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "вывести значения поля oscarsCount всех элементов в порядке возрастания";
    }
}