package tools.commands;

import tools.Command;
import tools.CommandInvoker;
import tools.ParametrizedCommand;

import java.io.Serializable;

public class Help extends Command implements Serializable {
    public Help(){
        super("Help","Prints information about available commands.");
        hasData = false;
    }
    @Override
    public void execute(){
        CommandInvoker.getCommands().values().forEach(w -> res += "\n" + w.getName() + ": " + w.getDescription());
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
