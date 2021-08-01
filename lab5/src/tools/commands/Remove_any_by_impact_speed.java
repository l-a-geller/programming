package tools.commands;

import data.HumanBeing;
import data.HumanBeingStorage;

public class Remove_any_by_impact_speed extends AbstractCommand{
    public Remove_any_by_impact_speed(){
        super("Remove_by_impact_speed", "Удалить из коллекции элемент, значение поля impactSpeed (скорость) которого эквивалентно заданному");
    }

    @Override
    public void execute(String data){
        try {
            double speed = Double.parseDouble(data);
            HumanBeing humanToRemove = null;
            for (HumanBeing human : HumanBeingStorage.getData()){
                if (human.getImpactSpeed() == speed){
                    humanToRemove = human;
                    break;
                }
            }
            if (humanToRemove == null) System.out.println("Не найдено элементов с соответствующей impactSpeed (скоростью)");
            else HumanBeingStorage.remove(humanToRemove);
        }catch (NumberFormatException e){
            System.out.println("Скорость должна быть неотрицательным числом");
        }catch (NullPointerException ignored){}
    }
}
