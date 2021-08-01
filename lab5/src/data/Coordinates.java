package data;

/**
 * Класс, описывающий координаты
 */
public class Coordinates {
    private int x;
    private long y; //Значение поля должно быть больше -848
    public Coordinates(int xParam, long yParam){
        x = xParam;
        y = yParam;
    }

    public int getX() { return x; }
    public long getY() { return y; }

    public void setY(long y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
