package commands;

import app.App;
import app.CollectionManager;

/**
 * Класс команды Update, предназначенный для обновления значения элемента коллекции по его ID.
 */
public class Update implements Command {

    /**
     * Выполняет команду обновления элемента коллекции по указанному ID.
     *
     * @param argument строка, содержащая ID элемента для обновления
     */
    @Override
    public void execute(String argument) {
        try {
            CollectionManager collectionManager = App.getApp().getCollectionManager();
            if (collectionManager.findMovieById(Long.parseLong(argument)) == null) {
                System.out.println("В коллекции не содержится элемента с таким индексом");
            } else {
                collectionManager.updateElement(Long.parseLong(argument), collectionManager.findMovieById(Long.parseLong(argument)));
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
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
