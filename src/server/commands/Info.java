package server.commands;

import common.Response;
import common.collectionObject.Movie;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;

/**
 * Класс команды Info, предназначенный для вывода информации о коллекции.
 */
public class Info extends Command {

    /**
     * Выполняет команду вывода информации о коллекции, включая её тип, тип элементов, дату инициализации и количество элементов.
     */
    @Override
    public Response execute(String strArg, Serializable objArg)  {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        StringBuilder out = new StringBuilder();
        if (!collectionManager.getCollection().isEmpty()) {
            out.append("Type of collection: " + collectionManager.getCollection().getClass().getSimpleName() +
                    "\nType of elements: " + Movie.class.getSimpleName() +
                    "\nInitialization date: " + collectionManager.findMovieById(1L).getCreationDate() +
                    "\nNumber of collection items: " + collectionManager.getCollection().size());
        } else {
            return new Response("Коллекция не содержит элементов");
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
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
