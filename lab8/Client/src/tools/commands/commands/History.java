package tools.commands.commands;

import tools.commands.Command;
import tools.commands.CommandInvoker;

public class History extends Command {
    public History(){
        super("History", "Prints last 7 coomands without arguments");
        hasData = false;
    }
    @Override
    public void execute(){
    }
    @Override
    public String getAnswer(){
        return res;
    }
}
