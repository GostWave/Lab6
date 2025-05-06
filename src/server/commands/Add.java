package server.commands;

import common.Response;
import common.collectionObject.Movie;
import server.CollectionManager;
import server.Server;

import java.io.Serializable;

public class Add extends Command {


    @Override
    public Response execute(String strArg, Serializable objArg)  {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        Movie movie = (Movie) objArg;
        collectionManager.addMovie(movie);
        return new Response("Элемент успешно добавлен в коллекцию");
    }


    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
