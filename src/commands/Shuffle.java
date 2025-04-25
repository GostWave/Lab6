package commands;

import app.App;
import app.CollectionManager;

/**
 * Класс команды Shuffle, предназначенный для перемешивания элементов коллекции в случайном порядке.
 */
public class Shuffle implements Command {

    /**
     * Выполняет команду перемешивания элементов коллекции.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        if (!collectionManager.getCollection().isEmpty()) {
            collectionManager.shuffleCollection();
            System.out.println("Коллекция успешно перемешана");
        } else {
            System.out.println("Коллекция пуста, её нельзя перемешать");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
    }
}