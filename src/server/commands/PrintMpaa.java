package server.commands;

import common.Response;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Класс команды PrintMpaa, предназначенный для вывода уникальных значений поля mpaaRating всех элементов в коллекции.
 */
public class PrintMpaa extends Command {

    /**
     * Выполняет команду, выводя уникальные значения поля mpaaRating из коллекции.
     */
    @Override
    public Response execute(String strArg, Serializable objArg) {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        HashSet<String> set = collectionManager.uniqueMpaa();
        StringBuilder out = new StringBuilder();
        if (collectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пуста, невозможно вывести уникальные mpaaRating");
        } else {
            for (String mpaa : set) {
                out.append(mpaa+"\n");
            }
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
        return "вывести уникальные значения поля mpaaRating всех элементов в коллекции";
    }
}