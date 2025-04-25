package commands;

import app.App;
import app.CollectionManager;
import collectionObject.Movie;

/**
 * Класс команды Insert, предназначенный для добавления нового элемента в коллекцию
 * на заданную позицию.
 */
public class Insert implements Command {

    /**
     * Выполняет команду вставки нового элемента в коллекцию на указанную позицию.
     *
     * @param argument строка, содержащая позицию для вставки элемента
     */
    @Override
    public void execute(String argument) {
        try {
            CollectionManager collectionManager = App.getApp().getCollectionManager();
            Movie movie = collectionManager.createMovie();
            collectionManager.insertElement(movie, Integer.parseInt(argument));
        } catch (NumberFormatException e) {
            System.out.println("Введён неверный аргумент команды");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в заданную позицию";
    }
}
