package data;

/**
 * Класс, описывающий машину
 */
public class Car {
    private boolean cool;

    public Car(boolean coolness){
        cool = coolness;
    }

    public Boolean isCool() {
        return cool;
    }

    @Override
    public String toString() {
        return (cool) ? "Крутая":"Не крутая";
    }
}
