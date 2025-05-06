package server.commands;

import common.Response;
import server.*;
import server.CommandManager;
import server.Server;

import java.io.Serializable;
import java.util.Map;

/**
 * Класс команды Help, предназначенный для вывода справки по доступным командам.
 */
public class Help extends Command {

    /**
     * Выполняет команду вывода списка доступных команд и их описаний.
     */
    @Override
    public Response execute(String strArg, Serializable objArg) {
        CommandManager commandManager = Server.getServer().getCommandManager();
        Map<String, Command> commands = commandManager.getCommands();

        StringBuilder out = new StringBuilder();
        out.append("Список доступных команд:\n");

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            out.append(entry.getKey() + " : " + entry.getValue().getDescription()).append("\n");
        }
        return new Response(out.toString());
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
