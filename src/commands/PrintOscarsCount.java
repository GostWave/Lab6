package commands;

import app.App;
import app.CollectionManager;

/**
 * Класс команды PrintOscarsCount, предназначенный для вывода значений поля oscarsCount
 * всех элементов коллекции в порядке возрастания.
 */
public class PrintOscarsCount implements Command {

    /**
     * Выполняет команду, выводя значения поля oscarsCount всех элементов в коллекции.
     */
    @Override
    public void execute(String argument) {
        CollectionManager collectionManager = App.getApp().getCollectionManager();
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция пуста, невозможно вывести oscarsCount");
        } else {
            collectionManager.printOscarsCount();
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "вывести значения поля oscarsCount всех элементов в порядке возрастания";
    }
}