package tools.commands;

import data.HumanBeing;
import data.HumanBeingStorage;

public class Remove_by_id extends AbstractCommand {
    public Remove_by_id(){
        super("Remove_by_id", "Удалить элемент из коллекции по его id");
    }

    @Override
    public void execute(String data){
        try {
            int id = Integer.parseInt(data);
            HumanBeing human = HumanBeingStorage.searchById(id);
            HumanBeingStorage.remove(human);
        }catch (NumberFormatException e){
            System.out.println("Id должен быть целым числом");
        }catch (NullPointerException ignored){}
    }
}
