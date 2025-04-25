package app;

import commands.Command;

/**
 * Класс, отвечающий за обработку ввода команд из консоли.
 * Получает текст команды, определяет её и выполняет соответствующее действие.
 */
public class ConsoleCaller {

    /**
     * Менеджер команд, отвечающий за хранение и выполнение команд.
     */
    private final CommandManager commandManager = App.getApp().getCommandManager();

    /**
     * Обрабатывает введённую пользователем команду и выполняет её.
     *
     * @param text строка, содержащая команду и её аргумент (если есть)
     */
    public void call(String text) {
        String[] request = text.toLowerCase().split(" ");
        String argument = "";

        if (request.length > 1) {
            argument = request[1].trim();
        }

        try {
            Command command = commandManager.getCommandByKey(request[0]);
            command.execute(argument);
        } catch (NullPointerException e) {
            System.out.println("Нет такой команды: попробуй ещё раз");
        }
    }
}
