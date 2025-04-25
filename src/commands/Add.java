package commands;

import app.App;
import app.CollectionManager;
import collectionObject.Movie;

/**
 * Класс команды Add, предназначенный для добавления нового элемента в коллекцию.
 */
public class Add implements Command {

    /**
     * Выполняет команду добавления нового элемента в коллекцию.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        Movie movie = collectionManager.createMovie();
        collectionManager.addMovie(movie);
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
