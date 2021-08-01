package tools.commands;

import data.HumanBeing;
import data.HumanBeingStorage;
import tools.execution.DefaultComparator;
import tools.execution.Loader;

import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Remove_greater extends AbstractCommand {
    public Remove_greater(){
        super("Remove_greater", "Удалить из коллекции все элементы, большие, чем заданный");
    }
    @Override
    public void execute(){
        System.out.println("Пожалуйста введите значеня полей персонажа, с которым будет производиться сравнение остальных элементов колллекции");
        Loader loader = new Loader();
        DefaultComparator defaultComparator = new DefaultComparator();
        HumanBeing humanBeing = loader.search(new Scanner(System.in));

        Iterator<HumanBeing> humanBeingIterator = HumanBeingStorage.getData().iterator();
        while (humanBeingIterator.hasNext()){
            HumanBeing human = humanBeingIterator.next();
            if (defaultComparator.compare(humanBeing, human) < 0) {
                humanBeingIterator.remove();
                System.out.println("Персонаж " + human.getName() + " удален");
            }
        }
    }
}
