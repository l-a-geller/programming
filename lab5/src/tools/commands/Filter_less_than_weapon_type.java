package tools.commands;

import data.HumanBeing;
import data.HumanBeingStorage;

import java.util.ArrayList;

public class Filter_less_than_weapon_type extends AbstractCommand{
    public Filter_less_than_weapon_type(){
        super("Filter_less_than_weapon_type", "Вывести элементы, значение поля weaponType (тип  оружия) которых меньше заданного");
    }
    @Override
    public void execute(String data){
        boolean found = false;
        for (HumanBeing human: HumanBeingStorage.getData()){
            if (human.getWeaponType() != null && human.getWeaponType().ordinal() == Integer.parseInt(data)){
                human.print();
                found = true;
            }
        }
        if (!found){
            System.out.println("Персонажей с соответствующим Weapon_type не обнаружено");
        }
    }
}
