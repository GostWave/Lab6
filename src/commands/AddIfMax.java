package commands;

import app.App;
import app.CollectionManager;
import collectionObject.Movie;

/**
 * Класс команды AddIfMax, предназначенный для добавления нового элемента в коллекцию,
 * если его значение превышает значение наибольшего элемента коллекции.
 */
public class AddIfMax implements Command {

    /**
     * Выполняет команду добавления элемента, если его значение превышает максимальное в коллекции.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        Movie movie = collectionManager.createMovie();
        if (movie.getOscarsCount() > collectionManager.getMaxOscarsCount()) {
            collectionManager.addMovie(movie);
        } else {
            System.out.println("Элемент не был добавлен в коллекцию");
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
