package tools.execution;

import data.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Loader {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero; //Поле не может быть null
    private Boolean hasToothpick;
    private double impactSpeed; //Значение поля должно быть больше -228
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле не может быть null
    private Car car; //Поле не может быть null


    public HumanBeing search(Scanner sc){
        HumanBeing hum = new HumanBeing();
        String line;


        System.out.println("Пожалуйста введите имя: ");

        name = scanName(sc);


        System.out.println("Пожалуйста введите X (Целое число):");
        Integer x = scanX(sc);

        System.out.println("Пожалуйста введите Y (Целое число):");
        Long y = scanY(sc);

        coordinates = new Coordinates(x, y);

        System.out.println("Добавить персонажу зубочистку? ( Напишите yes если да ):");


        line = sc.nextLine();
        if (line.trim().equals("yes")){
            hasToothpick = true;
        }else{
            hasToothpick = false;

        }


        System.out.println("Пожалуйста введите realHero (true/false):");
        while (true){
            line = sc.nextLine();
            if (line.trim().equals("true")){
                realHero = true;
                break;
            }
            if (line.trim().equals("false")){
                realHero = false;
                break;
            }else {
                System.out.println("Поле обязательно. Допустимые значения: true или false");
            }
        }



        System.out.println("Пожалуйста введите impactSpeed (Double):");
        while (true){
            try{
                line = sc.nextLine();
                impactSpeed = Double.parseDouble(line);
                if (impactSpeed > -228){
                    break;
                }else {
                    System.out.println("Значение должно быть > -228");
                }
            } catch (NumberFormatException e){ System.out.println("impactSpeed должен быть числом с плавающей точкой, > -228"); }
        }


        System.out.println("Пожалуйста введите Тип оружия ( HAMMER, PISTOL, KNIFE, BAT ) или пустую строку чтобы пропустить поле");
        while (true){
            try{
                line = sc.nextLine();
                if (line.trim().equals("")) {
                    break;
                }
                weaponType = WeaponType.valueOf(line);
                break;
            }catch (IllegalArgumentException e){ System.out.println("введите одно из значений: ( HAMMER, PISTOL, KNIFE, BAT )"); }
        }

        System.out.println("Пожалуйста введите Настроение ( SORROW, GLOOM, RAGE, FRENZY )");
        while (true){
            try{
                line = sc.nextLine();
                mood = Mood.valueOf(line);
                break;
            }catch (IllegalArgumentException e){ System.out.println("введите одно из значений: ( SORROW, GLOOM, RAGE, FRENZY )"); }

        }


        System.out.println("Пожалуйста введите крутая ли машина у персонажа ( true/false )");
        boolean carCoolness;
        while (true){

            line = sc.nextLine();
            if (line.trim().equals("true")){
                carCoolness = true;
                break;
            }
            if (line.trim().equals("false")){
                carCoolness = false;
                break;
            }else {
                System.out.println("Поле обязательно. Допустимые значения: true или false");
            }
        }
        car =  new Car(carCoolness);

        long id = 1;
        for (HumanBeing h: HumanBeingStorage.getData()){
            if (h.getId() > id) id = h.getId();
        }
        hum = new HumanBeing(name, coordinates, LocalDateTime.now(), realHero, impactSpeed, weaponType, mood, car);
        try {
            hum.setHasToothpick(hasToothpick);
        }catch (NullPointerException e){}

        hum.setId(++id);
        return hum;
    }
    public void load(HumanBeing hum){
        HumanBeingStorage.put(hum);
    }

    public String scanName(Scanner sc){
        String line;
        String name;
        while (true){
            try{
                line = sc.nextLine();
                name = line.trim();
                if (name.equals("")){
                    throw new NumberFormatException();
                }
                break;
            }catch (NumberFormatException e){ System.out.println("Имя должно быть не пустой строкой,повторите ввод."); }
        }
        return name;
    }

    public int scanX(Scanner sc){
        Integer x;
        String line;
        while (true){
            try{
                line = sc.nextLine();
                x = Integer.parseInt(line);
                break;
            } catch (NumberFormatException e){ System.out.println("X должен быть целым числом"); }
        }
        return x;
    }

    public Long scanY(Scanner sc){
        Long y;
        String line;
        while (true){

            try {
                line = sc.nextLine();
                if (Long.parseLong(line) > -848) {
                    y = Long.parseLong(line);
                    break;
                }else {
                    System.out.println("Y должен быть > -848");
                }
            }catch (NumberFormatException e){
                System.out.println("Y должен быть целым числом > -848");
            }
        }
        return y;
    }

}
