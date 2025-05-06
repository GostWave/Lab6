package server;

import common.Request;
import common.Response;
import server.commands.Command;
import server.commands.Save;

import java.util.Scanner;


public class ConsoleCaller implements Runnable{

    private final Save save;

    public ConsoleCaller(Save save) {
        this.save = save;
    }
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("save")) {
                Response response = save.execute(null, null);
                System.out.println(response.getMessage());
            } else {
                System.out.println("Неизвестная команда для сервера.");
            }
        }
    }
}
