package commands;

import app.App;
import app.CommandManager;

import java.util.Map;

/**
 * Класс команды Help, предназначенный для вывода справки по доступным командам.
 */
public class Help implements Command {

    /**
     * Выполняет команду вывода списка доступных команд и их описаний.
     */
    @Override
    public void execute(String argument) {
        CommandManager commandManager = App.getApp().getCommandManager();
        Map<String, Command> commands = commandManager.getCommands();
        System.out.println("Список доступных команд:");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue().getDescription());
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
