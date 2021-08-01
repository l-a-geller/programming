package tools.commands;

import data.HumanBeing;
import data.HumanBeingStorage;
import tools.execution.Loader;
import java.util.Scanner;

public class Update extends AbstractCommand{
    public Update(){
        super("Update", "Обновить значение элемента коллекции, id которого равен заданному");
    }
    @Override
    public void execute(String data){
        try {
            int id = Integer.parseInt(data);
            HumanBeing hum = HumanBeingStorage.searchById(id);
            if (hum != null){
                Scanner sc = this.sc;
                System.out.println("Введите какое поле хотите изменить: ( name, x, y )");
                String line;
                Loader loader = new Loader();
                line = sc.nextLine();
                if (line.trim().toUpperCase().equals("NAME")){
                    System.out.println("Введите новое имя");
                    String name = loader.scanName(sc);
                    hum.setName(name);
                    System.out.println("Новое имя успешно установлено");
                }else  if (line.trim().toUpperCase().equals("X")){
                    System.out.println("Введите новый X");
                    int x = loader.scanX(sc);
                    hum.getCoordinates().setX(x);
                    System.out.println("Новый X успешно установлен");
                }else  if (line.trim().toUpperCase().equals("Y")){
                    System.out.println("Введите новое имя");
                    long y = loader.scanY(sc);
                    hum.getCoordinates().setY(y);
                    System.out.println("Новый Y успешно установлен");
                }
            }

        }catch (NumberFormatException e){
            System.out.println("Please provide valid id");
        }
    }
}
