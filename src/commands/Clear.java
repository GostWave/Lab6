package commands;

import app.App;
import app.CollectionManager;

/**
 * Класс команды Clear, предназначенный для очистки коллекции.
 */
public class Clear implements Command {

    /**
     * Выполняет команду очистки коллекции.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция не содержит элементов, которые можно было бы очистить");
        } else {
            collectionManager.clearCollection();
            System.out.println("Коллекция успешно очищена");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
