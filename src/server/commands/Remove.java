package server.commands;

import common.Response;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;

public class Remove extends Command {


    @Override
    public Response execute(String strArg, Serializable objArg) {
        try {
            CollectionManager collectionManager = Server.getServer().getCollectionManager();
            if (collectionManager.findMovieById(Long.parseLong(strArg)) == null) {
                return new Response("В коллекции не содержится элемента с таким индексом");
            } else {
                collectionManager.removeElement(collectionManager.findMovieById(Long.parseLong(strArg)));
                return new Response("Элемент успешно удалён из коллекции");
            }
        } catch (NumberFormatException e) {
            return new Response("Введён неверный аргумент команды");
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
