package tools.commands.commands;

import data.LabworksStorage;
import data.LabWork;
import tools.DataBase.DataBaseConnector;
import tools.commands.Command;

public class Update extends Command{
    public Update(){
        super("Update", "Updates element of collection with IP provided");
        hasData = true;
    }
    @Override
    public void execute(String data){
        this.res = "";
        try {
            String[] ss = data.split(" ");
            int id = Integer.parseInt(ss[0]);
            LabWork lab = LabworksStorage.searchById(id);
            if (lab == null){
                throw new NumberFormatException();
            }
            if (login.equals(lab.getUser())){
                if (ss[1].equals("NAME")){
                    res += lab.update(ss[2]);
                    DataBaseConnector.updateLab(lab, ss[2]);
                }
            }else {
                res += "Sorry, Labwork doesn't belong to you";
            }
        }catch (NumberFormatException e){
            res += "Please provide valid id";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}