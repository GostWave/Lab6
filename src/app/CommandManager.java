package app;

import commands.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для управления командами в приложении.
 * Позволяет регистрировать, получать и хранить команды.
 */
public class CommandManager {

    /**
     * Хранит список доступных команд, где ключ — название команды, а значение — соответствующий объект команды.
     */
    private Map<String, Command> commands = new HashMap<>();

    /**
     * Регистрирует новую команду в менеджере.
     *
     * @param key     название команды (регистр не имеет значения)
     * @param command объект команды, реализующий интерфейс {@link Command}
     */
    public void registerCommand(String key, Command command) {
        commands.put(key.toLowerCase(), command);
    }

    /**
     * Получает команду по её названию.
     *
     * @param key название команды
     * @return объект команды по ключу
     */
    public Command getCommandByKey(String key) {
        return commands.get(key.toLowerCase());
    }

    /**
     * Возвращает все зарегистрированные команды.
     *
     * @return зарегистрированные команды
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}
