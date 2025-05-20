package server.commands;

import common.Request;
import common.Response;
import common.collectionObject.*;

import server.CommandManager;
import server.IO.CollectionChecker;
import server.CollectionManager;
import server.ConsoleCaller;
import server.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Scanner;

public class ExecuteScript extends Command {
    ConsoleCaller consoleCaller = Server.getServer().getConsoleCaller();
    CollectionManager collectionManager = Server.getServer().getCollectionManager();
    CommandManager commandManager = Server.getServer().getCommandManager();
    Movie movie;

    @Override
    public Response execute(String strArg, Serializable objArg) {
        HashSet<String> callScripts= new HashSet<>();
        StringBuilder out = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(strArg));

            while (scanner.hasNext()) {
                String text = scanner.nextLine().trim();

                if (text.length()>4 && text.split(" ").length>1 && text.substring(text.length() - 4).equals(".txt")){
                    callScripts.add(text.split(" ")[1]);
                }
                if (text.length()>4 && text.split(" ").length>1 && text.substring(text.length() - 4).equals(".txt") && callScripts.contains(text.split(" ")[1])){
                    return new Response("Возникла рекурсия");
                } else if (text.equals("add")){
                    if (element(scanner)){
                        collectionManager.addMovie(movie);
                    }
                } else if (text.contains("update")) {
                    if (element(scanner)){
                        try {
                            collectionManager.updateElement(Long.parseLong(text.split(" ")[1]),movie, null);
                        } catch (NumberFormatException e) {
                            return new Response("Ошибка при выполнении скрипта");
                        }
                    }
                } else if (text.contains("insert")){
                    try {
                        collectionManager.insertElement(movie,Integer.parseInt(text.split(" ")[1]));
                    } catch (NumberFormatException e) {
                        return new Response("Ошибка при выполнении скрипта");
                    }
                } else if (text.contains("add_if_max")){
                    try {
                        if (movie.getOscarsCount() > collectionManager.getMaxOscarsCount()) {
                            collectionManager.addMovie(movie);
                        } else {
                            return new Response("Элемент не был добавлен в коллекцию");
                        }
                    } catch (NumberFormatException e) {
                        return new Response("Ошибка при выполнении скрипта");
                    }
                } else {
                    String strCommand = "";
                    String argument = "";
                    if (text.split(" ").length>1){
                        strCommand=text.split(" ")[0];
                        argument = text.split(" ")[1];
                    }
                    Command command = commandManager.getCommandByKey(text);
                    if (command!=null){
                        out.append(command.execute(argument, null).getMessage()+"\n\n");
                    } else {
                        out.append("Неверное имя команды");
                        break;
                    }

                }


            }
        } catch (FileNotFoundException e) {
            return new Response("Введено неверное имя файла или необходимый файл отсутствует");
        }
        return new Response(out.toString());
    }


    public boolean element(Scanner scanner){
        movie=new Movie();
        movie.setId(collectionManager.getCurrentid());
        String text;
        movie.setName(scanner.nextLine().trim());
        Coordinates coordinates = new Coordinates();
        text = scanner.nextLine().trim();
        try{
            coordinates.setX(Double.parseDouble(text));
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        text = scanner.nextLine().trim();
        try{
            coordinates.setY(Long.parseLong(text));
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        movie.setCoordinates(coordinates);
        text = scanner.nextLine().trim().toUpperCase();
        try {
            movie.setGenre(MovieGenre.valueOf(text));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        text = scanner.nextLine().trim();
        try {
            movie.setOscarsCount(Integer.parseInt(text));
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        text = scanner.nextLine().trim();
        try {
            movie.setMpaaRating(MpaaRating.valueOf(text));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        Person person = new Person();
        text = scanner.nextLine().trim();
        try {
            person.setName(text);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        text = scanner.nextLine().trim();
        try {
            String[] content = text.split("\\.");
            if (content.length != 3) {
                System.out.println("Ошибка при выполнении скрипта");
                return false;
            }
            ZonedDateTime date = ZonedDateTime.of(Integer.parseInt(content[2]), Integer.parseInt(content[1]), Integer.parseInt(content[0]), 0, 0, 0, 0, ZoneId.of("Europe/Moscow"));
            if (date.isAfter(ZonedDateTime.now())) {
                System.out.println("Ошибка при выполнении скрипта");
                return false;
            }
            person.setBirthday(date);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        text = scanner.nextLine().trim();
        try {
            person.setPassportID(text);
        } catch (Exception e){
            System.out.println("Ошибка при выполнении скрипта");
            return false;
        }
        movie.setOperator(person);
        movie.setCreationDate(LocalDate.now());
        System.out.println(movie.toString());
        CollectionChecker collectionChecker = new CollectionChecker(new Movies());
        if (collectionChecker.checkMovie(movie)) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }
}
