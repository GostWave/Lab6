package server.commands;

import common.Response;
import server.IO.FileManager;
import server.Server;

import java.io.Serializable;


public class Save extends Command {


    @Override
    public Response execute(String strArg, Serializable objArg) {
        FileManager fileManager = Server.getServer().getFileManager();
        fileManager.fileWriter();
        return new Response("");
    }


    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
