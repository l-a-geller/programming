package tools.commands;


import data.HumanBeingStorage;

public class Clear extends AbstractCommand {
    public Clear(){
        super("Clear","Очистить коллекцию");
    }
    @Override
    public void execute(){
        HumanBeingStorage.clear();
        System.out.println("Коллекция очищена.\n");
    }
}
