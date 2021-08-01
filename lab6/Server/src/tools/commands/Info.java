package tools.commands;

import data.LabworksStorage;
import tools.Command;

import java.io.Serializable;

public class Info extends Command implements Serializable {
    public Info(){
        super("Info","Prints Info");
        hasData = false;
    }
    @Override
    public void execute() {
        res += LabworksStorage.printInfo();
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
