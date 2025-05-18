package server.commands;

import common.Response;

import common.collectionObject.Movie;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;


public class Update extends Command {
    @Override
    public Response execute(String strArg, Serializable objArg) {
        try {
            CollectionManager collectionManager = Server.getServer().getCollectionManager();
            if (collectionManager.findMovieById(Long.parseLong(strArg)) == null) {
                return new Response("В коллекции не содержится элемента с таким индексом");
            } else {
                collectionManager.updateElement(Long.parseLong(strArg), collectionManager.findMovieById(Long.parseLong(strArg)), (Movie) objArg);
                return new Response("Элемент успешно обновлён");
            }
        } catch (NumberFormatException e) {
            return new Response("Введён неверный аргумент команды");
        }
    }


    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
