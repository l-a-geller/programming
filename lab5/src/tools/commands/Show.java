package tools.commands;

import data.HumanBeingStorage;

public class Show extends AbstractCommand {
    public Show(){
        super("Show","Вывести все элементы коллекции в строковом представлении");
    }
    @Override
    public void execute(){
        HumanBeingStorage.print();
    }
}
