package server;

import server.IO.FileManager;
import server.commands.*;

/**
 * Главный класс приложения, отвечающий за инициализацию и запуск программы.
 * В этом классе создаются основные компоненты, регистрируются команды и начинается работа консольного приложения.
 */
public class Main {

    /**
     * Здесь выполняется настройка и запуск приложения.
     */
    public static void main(String[] args) {


        Server server = Server.getServer();


        CommandManager commandManager = new CommandManager();
        server.setCommandManager(commandManager);


        server.setCollectionManager(new CollectionManager());
        server.setFileManager(new FileManager());
        server.setConsoleCaller(new ConsoleCaller(new Save()));


        Help help = new Help();


        commandManager.registerCommand("help", help);
        commandManager.registerCommand("info", new Info());
        commandManager.registerCommand("add", new Add());
        commandManager.registerCommand("show", new Show());
        commandManager.registerCommand("clear", new Clear());
        commandManager.registerCommand("shuffle", new Shuffle());
        commandManager.registerCommand("update_id", new Update());
        commandManager.registerCommand("remove_by_id", new Remove());
        commandManager.registerCommand("print_unique_mpaa_rating", new PrintMpaa());
        commandManager.registerCommand("print_field_ascending_oscars_count", new PrintOscarsCount());
        commandManager.registerCommand("execute_script", new ExecuteScript());
        commandManager.registerCommand("group_counting_by_oscarsCount", new GroupCounting());
        commandManager.registerCommand("add_if_max", new AddIfMax());





        FileManager fileManager = new FileManager();
        fileManager.importCollection();


        server.start();
    }
}
