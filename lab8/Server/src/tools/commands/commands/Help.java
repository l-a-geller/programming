package tools.commands.commands;

import tools.commands.Command;
import tools.commands.CommandInvoker;

import java.io.Serializable;

public class Help extends Command implements Serializable {
    public Help(){
        super("Help","Prints information about available commands.");
        hasData = false;
    }
    @Override
    public void execute(){
        this.res = "";
        CommandInvoker.getCommands().values().forEach(w -> res += "\n" + w.getName() + ": " + w.getDescription());
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
