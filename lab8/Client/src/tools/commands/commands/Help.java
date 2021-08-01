package tools.commands.commands;

import tools.commands.Command;
import tools.commands.CommandInvoker;
//import tools.ParametrizedCommand;

import java.io.Serializable;

public class Help extends Command implements Serializable {
    public Help(){
        super("Help","Prints information about available commands.");
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
