package tools.commands.commands;

import tools.commands.Command;

public class Add extends Command {
   
    public Add(){
        super("Add","Adds an element");
        hasData = false;
        needsScanner = true;
    }

    public void execute(){
        res = "LabWork added";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
