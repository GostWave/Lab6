package commands;

import IO.FileManager;
import app.App;

/**
 * Класс команды Save, предназначенный для сохранения коллекции в файл.
 */
public class Save implements Command {

    /**
     * Выполняет команду сохранения коллекции в файл.
     */
    @Override
    public void execute(String argument) {
        FileManager fileManager = App.getApp().getFileManager();
        fileManager.fileWriter();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
