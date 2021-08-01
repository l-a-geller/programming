package tools.commands.commands;

import data.LabWork;
import data.LabworksStorage;
import tools.DataBase.DataBaseConnector;
import tools.commands.Command;

import java.util.ArrayList;

public class Show extends Command {
    public Show(){
        super("Show","Shows elements of collection");
        hasData = false;
    }
    @Override
    public void execute(){
        this.res = "";
        String oldRes = res;
        ArrayList<LabWork> labWorks = DataBaseConnector.readLab();
        LabworksStorage.clear();
        labWorks.stream().forEach(labWork -> LabworksStorage.put(labWork));
        labWorks.stream().forEach(labWork -> res += labWork.print());
        if (oldRes.equals(res)){
            res += "Collection empty";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
