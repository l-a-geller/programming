package data;

import java.time.LocalDateTime;
import java.util.Vector;

public class HumanBeingStorage {
    private static final LocalDateTime localDateTime;
    private static Vector<HumanBeing> humanBeings = new Vector<HumanBeing>();

    static {
        localDateTime = LocalDateTime.now();
    }
    public static void setHumanBeings(Vector<HumanBeing> humanBeings) {
        HumanBeingStorage.humanBeings = humanBeings;
    }
    public static void printInfo() {
        System.out.println("Тип коллекции: " + humanBeings.getClass().getName() + "\nДата инициализации: " + localDateTime + "\nКоличество элементов: " + humanBeings.size());
    }
    public static void print() {
        if (humanBeings.size() == 0) System.out.println("Коллекция пуста.");
        for (HumanBeing h: humanBeings)
            h.print();
    }

    public static void clear() {
        humanBeings.clear();
    }

    public static HumanBeing searchById(int id) {
        boolean found = false;
        HumanBeing laboratory = null;
        for(HumanBeing hum: humanBeings) {
            if (hum.checkId(id)){
                laboratory = hum;
                found = true;
                break;
            }
        }
        if (!found){ System.out.println("Объекта с таким ID не обнаружено."); }
        return laboratory;
    }

    public static void remove(HumanBeing hum) {
        humanBeings.remove(hum);
        System.out.println("Элемент с Id : " + hum.getId() + " удален.");
    }

    public static Vector<HumanBeing> getData() {
        return humanBeings;
    }

    public static void put(HumanBeing l) {
        boolean added = humanBeings.add(l);
        if (added){ System.out.println("HumanBeing " + l.getName() + " добавлен"); }
        else { System.out.println("HumanBeing уже существует."); }
    }
}
