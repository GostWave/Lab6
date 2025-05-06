package server.commands;

import common.Response;
import common.collectionObject.Movie;

import server.CollectionManager;
import server.Server;

import java.io.Serializable;

/**
 * Класс команды AddIfMax, предназначенный для добавления нового элемента в коллекцию,
 * если его значение превышает значение наибольшего элемента коллекции.
 */
public class AddIfMax extends Command {

    /**
     * Выполняет команду добавления элемента, если его значение превышает максимальное в коллекции.
     */
    @Override
    public Response execute(String strArg, Serializable objArg) {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        Movie movie = (Movie) objArg;
        if (movie.getOscarsCount() > collectionManager.getMaxOscarsCount()) {
            collectionManager.addMovie(movie);
            return new Response("Элемент успешно добавлен в коллекцию");
        } else {
            return new Response("Элемент не был добавлен в коллекцию");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
