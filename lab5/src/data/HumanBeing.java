package data;

import java.time.LocalDateTime;

/**
 * Класс, описывающий человека
 */
public class HumanBeing {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero; //Поле не может быть null
    private boolean hasToothpick;
    private double impactSpeed; //Значение поля должно быть больше -228
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле не может быть null
    private Car car; //Поле не может быть null

    public HumanBeing(){

    }
    public HumanBeing(String name, Coordinates coordinates, LocalDateTime localDateTime, boolean realHero, boolean hasToothpick, double impactSpeed, WeaponType weaponType, Mood mood, Car car){
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = localDateTime;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public HumanBeing(String name, Coordinates coordinates, LocalDateTime now, Boolean realHero, double impactSpeed, WeaponType weaponType, Mood mood, Car car) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = now;
        this.realHero = realHero;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public Long getId(){
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public String getName() { return name; }
    public Coordinates getCoordinates(){ return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public Boolean getRealHero() { return realHero; }
    public Boolean doesHaveToothpick() { return hasToothpick; }
    public double getImpactSpeed() { return impactSpeed; }
    public WeaponType getWeaponType() { return weaponType; }
    public Mood getMood() { return mood; }
    public Car getCar(){return car;}


    public void print(){
        System.out.println("ID: "+ id +"\nИмя: " + name + "\nКоординаты: " + coordinates.getX() + " : " + coordinates.getY() + "\nДата создания: " + creationDate +"\nНастоящий герой: " + realHero + "\nЗубочистка: " + hasToothpick + "\nСкорость: " + impactSpeed + "\nТип оружия: " + weaponType + "\nНастроение: " + mood + "\nМашина: " + car.toString() + "\n");
    }

    public boolean checkId(int identificator){
        return identificator == this.id;
    }

    public void setName(String name){
        this.name = name;
    }
}