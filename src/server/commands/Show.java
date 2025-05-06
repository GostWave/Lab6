package server.commands;

import common.Response;

import server.CollectionManager;
import server.Server;

import java.io.Serializable;


public class Show extends Command {


    @Override
    public Response execute(String strArg, Serializable objArg) {
        CollectionManager collectionManager = Server.getServer().getCollectionManager();
        if (collectionManager.getCollection().isEmpty()) {
            return new Response("Коллекция пуста, нечего выводить");
        } else {
            StringBuilder out = new StringBuilder();
            for (Object element : collectionManager.getCollection()) {
                out.append(element.toString()).append("\n");
            }
            return new Response(out.toString());
        }
    }


    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
