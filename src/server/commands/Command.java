package server.commands;

import common.Response;

import java.io.Serializable;


public abstract class Command {


    public abstract Response execute(String strArg, Serializable objArg);

    public abstract String getDescription();
}
