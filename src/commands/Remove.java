package commands;

import app.App;
import app.CollectionManager;

/**
 * Класс команды Remove, предназначенный для удаления элемента из коллекции по его ID.
 */
public class Remove implements Command {

    /**
     * Выполняет команду удаления элемента из коллекции по указанному ID.
     *
     * @param argument строка, содержащая ID элемента для удаления
     */
    @Override
    public void execute(String argument) {
        try {
            CollectionManager collectionManager = App.getApp().getCollectionManager();
            if (collectionManager.findMovieById(Long.parseLong(argument)) == null) {
                System.out.println("В коллекции не содержится элемента с таким индексом");
            } else {
                collectionManager.removeElement(collectionManager.findMovieById(Long.parseLong(argument)));
                System.out.println("Элемент успешно удалён из коллекции");
            }
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
        return "удалить элемент из коллекции по его id";
    }
}
