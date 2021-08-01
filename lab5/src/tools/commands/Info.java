package tools.commands;

import data.HumanBeingStorage;

public class Info extends AbstractCommand {
    public Info(){
        super("Info","Вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }
    @Override
    public void execute() {
        HumanBeingStorage.printInfo();
    }
}
