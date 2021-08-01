package tools.commands.commands;

import data.LabWork;
import data.LabworksStorage;
import tools.commands.Command;

import java.util.Comparator;

public class Add extends Command {

    public Add(){
        super("Add","Adds an element");
        hasData = false;
        needsScanner = true;
    }

    public void execute(){
        res = "LabWork ";
        res += LabworksStorage.getData().stream().max(Comparator.comparing(LabWork::getId)).get().getId();
        res += " added";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
