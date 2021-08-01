package tools.commands;

import data.HumanBeingStorage;
import tools.execution.DefaultComparator;

import java.util.Collections;

public class Sort extends AbstractCommand{
    public Sort(){
        super("Sort", "Отсортировать коллекцию по умолчанию");
    }
    @Override
    public void execute(){
        Collections.sort(HumanBeingStorage.getData(), new DefaultComparator());
        System.out.println("Коллекция отсортирована в естественном порядке");
    }
}
