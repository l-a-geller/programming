package tools.commands;

import tools.Command;
import tools.CommandInvoker;

public class History extends Command {
    public History(){
        super("History", "Prints last 7 coomands without arguments");
        hasData = false;
    }
    @Override
    public void execute(){
        res += CommandInvoker.printSavedCommands();
    }
    @Override
    public String getAnswer(){
        return res;
    }
}