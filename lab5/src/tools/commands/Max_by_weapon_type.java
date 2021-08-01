package tools.commands;

import data.HumanBeing;
import data.HumanBeingStorage;

public class Max_by_weapon_type extends AbstractCommand {
    public Max_by_weapon_type(){
        super("Max_by_weapon_type", "Вывести элемент коллекции, значение поля weaponType которого является максимальным");
    }
    @Override
    public void execute(){
        HumanBeing maxWeaponHuman = null;
        Integer weaponType = -1;
        for (HumanBeing human: HumanBeingStorage.getData()){
            if (maxWeaponHuman == null) maxWeaponHuman = human;
            else if (human.getWeaponType() != null && maxWeaponHuman.getWeaponType() == null) maxWeaponHuman = human;
            else if (human.getWeaponType() != null && human.getWeaponType() != null && human.getWeaponType().ordinal() > maxWeaponHuman.getWeaponType().ordinal() ) maxWeaponHuman = human;
        }
        if (maxWeaponHuman == null){
            System.out.println("В коллекции нет персонажей с оружием");
        }else {
            maxWeaponHuman.print();
        }
    }
}
