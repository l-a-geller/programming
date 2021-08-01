package tools.commands;

import data.LabWork;
import tools.Command;
import java.util.Scanner;

public class Add extends Command {

    public Add(){
        super("Add","Adds an element");
        hasData = false;
        needsScanner = true;
    }

    public void execute(){

    }

    @Override
    public String getAnswer(){
        return res;
    }
}
