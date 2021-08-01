package tools.commands;

import data.HumanBeing;
import data.HumanBeingStorage;
import tools.execution.Loader;

import java.util.Scanner;

public class Insert_at_index extends AbstractCommand {
    public Insert_at_index() {
        super("Insert_at_index", "Добавить новый элемент в заданную позицию");
        needsScanner = true;
    }

    @Override
    public void execute(Scanner sc) {
        Loader loader = new Loader();
        HumanBeing hum = loader.search(sc);
        System.out.println("Введите на какую позицию в коллекции необходимо добавить элемент");
        int position;
        while (true){
            String next = sc.nextLine();
            position = Integer.parseInt(next);
            try{
                HumanBeingStorage.getData().add(position, hum);
                System.out.println("Элемент успешно добавлен");
                break;
            }catch (Exception e){
                // e.printStackTrace();
            }
        }
    }
}
