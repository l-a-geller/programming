package tools.commands.commands;

import data.LabworksStorage;
import tools.commands.Command;

public class Filter_By_Discipline extends Command {
    public Filter_By_Discipline(){
        super("Filter_by_discipline", "Prints elements with discipline field provided.");
        hasData = true;
    }
    @Override
    public void execute(String data){
        this.res = "";
        res += "Filtered by Discipline: " + data + "\n";
        String oldres = res;

        LabworksStorage.getData().stream().filter(w -> w.getDiscipline().toUpperCase().equals(data.trim().toUpperCase())).forEach(w -> res += w.print());
        if (oldres.equals(res)){
            res += "No Labworks with discipline: " + data.toUpperCase() + " found";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
