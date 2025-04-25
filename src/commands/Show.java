package commands;

import app.App;
import app.CollectionManager;

/**
 * Класс команды Show, предназначенный для вывода всех элементов коллекции в строковом представлении.
 */
public class Show implements Command {

    /**
     * Выполняет команду отображения всех элементов коллекции.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        collectionManager.showCollection();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
