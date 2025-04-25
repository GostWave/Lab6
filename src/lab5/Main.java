package lab5;

import IO.FileManager;
import app.App;
import app.CollectionManager;
import app.CommandManager;
import app.ConsoleCaller;
import commands.*;

/**
 * Главный класс приложения, отвечающий за инициализацию и запуск программы.
 * В этом классе создаются основные компоненты, регистрируются команды и начинается работа консольного приложения.
 */
public class Main {

    /**
     * Здесь выполняется настройка и запуск приложения.
     */
    public static void main(String[] args) {


        App app = App.getApp();


        CommandManager commandManager = new CommandManager();
        app.setCommandManager(commandManager);


        app.setCollectionManager(new CollectionManager());
        app.setFileManager(new FileManager());
        app.setConsoleCaller(new ConsoleCaller());


        Help help = new Help();


        commandManager.registerCommand("help", help);
        commandManager.registerCommand("info", new Info());
        commandManager.registerCommand("add", new Add());
        commandManager.registerCommand("show", new Show());
        commandManager.registerCommand("clear", new Clear());
        commandManager.registerCommand("save", new Save());
        commandManager.registerCommand("shuffle", new Shuffle());
        commandManager.registerCommand("update", new Update());
        commandManager.registerCommand("remove_by_id", new Remove());
        commandManager.registerCommand("insert_at", new Insert());
        commandManager.registerCommand("print_unique_mpaa_rating", new PrintMpaa());
        commandManager.registerCommand("print_field_ascending_oscars_count", new PrintOscarsCount());
        commandManager.registerCommand("execute_script", new ExecuteScript());
        commandManager.registerCommand("group_counting_by_oscarsCount", new GroupCounting());
        commandManager.registerCommand("add_if_max", new AddIfMax());


        help.execute("");


        FileManager fileManager = new FileManager();
        fileManager.importCollection();


        app.start();
    }
}
