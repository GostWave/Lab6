package commands;

import app.App;
import app.CollectionManager;
import collectionObject.Movie;

/**
 * Класс команды Info, предназначенный для вывода информации о коллекции.
 */
public class Info implements Command {

    /**
     * Выполняет команду вывода информации о коллекции, включая её тип, тип элементов, дату инициализации и количество элементов.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        if (!collectionManager.getCollection().isEmpty()) {
            System.out.println("Type of collection: " + collectionManager.getCollection().getClass().getSimpleName() +
                    "\nType of elements: " + Movie.class.getSimpleName() +
                    "\nInitialization date: " + collectionManager.findMovieById(1L).getCreationDate() +
                    "\nNumber of collection items: " + collectionManager.getCollection().size());
        } else {
            System.out.println("Коллекция не содержит элементов");
        }
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
