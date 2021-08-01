package tools.commands;

import data.LabworksStorage;
import tools.Command;

import java.io.Serializable;

public class Quit extends Command implements Serializable {
    public Quit(){
        super("Quit","Exits");
        hasData = false;
    }
    @Override
    public void execute() {
        //res += LabworksStorage.printInfo();
    }

    @Override
    public String getAnswer(){
        return res;
    }
}