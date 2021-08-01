package data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;

public class LabWork implements Comparable<LabWork>, Serializable {
    private int id;// = this.hashCode(); //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate;// = java.time.LocalDate.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float minimalPoint; //Поле не может быть null, Значение поля должно быть больше 0
    private long maximumPoint = 0; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Discipline discipline; //Поле может быть null
    private String author;
    public boolean drawen = false;

    public LabWork(String name, Coordinates coordinates, Float minimalPoint, long maximumPoint, Difficulty difficulty, Discipline discipline){
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.maximumPoint = maximumPoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
        id = this.hashCode();
        creationDate = java.time.LocalDate.now();
    }

    public LabWork(int id, String name, Coordinates coordinates, Float minimalPoint, long maximumPoint, Difficulty difficulty, Discipline discipline){
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.maximumPoint = maximumPoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
        this.id = id;
        creationDate = java.time.LocalDate.now();
    }

    public LabWork(String name, Coordinates coordinates, float minimalPoint, long maximumPoint) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.maximumPoint = maximumPoint;
        creationDate = java.time.LocalDate.now();
    }

    public long getMaximumPointer(){
        return maximumPoint;
    }
    public String getDateLoc(){
        return this.creationDate.toString();
    }
    public LabWork(){ }

    public String getName(){
        return this.name;
    }
    public int getId(){
        return this.id;
    }
    public int getCoordinate_x(){return this.coordinates.getX();}
    public float getCoordinate_y(){return this.coordinates.getY();}
    public LocalDate getDate(){return this.creationDate;}
    public Coordinates getCoordinates(){
        return this.coordinates;
    }
    public String getMinPoint(){
        return String.valueOf(this.minimalPoint);
    }
    public Float getMin(){return this.minimalPoint;}
    public String getMaxPoint(){
        return String.valueOf(this.maximumPoint);
    }
    public Long getMax(){return this.maximumPoint;}
    public String getDifficulty(){
        return this.difficulty.name();
    }
    public Difficulty getDiff(){ return this.difficulty;}
    public String getDiscipline(){
        return this.discipline.toString();
    }
    public Discipline getDisc() {return this.discipline;}
    public String getDisname() {return this.discipline.toString();}
    public Integer getDislec() {return this.discipline.getLecture();}
    public Integer getDisprac() {return this.discipline.getPractice();}
    public Long getDisself() {return this.discipline.getSelfStudy();}
    public String getAuthor() {return this.author;}
    public void setAuthor(String author){
        this.author = author;
    }
    public void setId(int id){
        this.id = id;
    }

    public String toString(){
        return ":name:"+name+":coordinates:"+coordinates.toString()+":creationDate:"+minimalPoint+":maximumPoint:"+maximumPoint+":difficulty:"+difficulty+":discipline:"+discipline;
    }
    public int compareTo(LabWork l){
        return (int)(l.name.compareToIgnoreCase(this.getName()));
    }

    public String print(){
        return "\nid "+ id +"\nname " + name + "\ncoordinates " + coordinates.toString() + "\ncreationDate " + creationDate +"\nminimalPoint " + minimalPoint + "\nmaximumPoint " + maximumPoint + "\ndifficulty " + difficulty + "\ndiscipline " + discipline + "\n";
    }

    public ArrayList<String> getFieldNames(){
        ArrayList<String> res = new ArrayList<>();
        for (Field f : this.getClass().getDeclaredFields()){
            try {
                if (f.get(this) == null){
                    res.add(f.getName());
                }else if ( f.getLong(this) == 0){
                    res.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                System.out.println("Field is damaged.");
            } catch (IllegalArgumentException e){
                //
            }
        }
        return res;
    }

    public ArrayList<String> getAllFieldNames(){
        ArrayList<String> res = new ArrayList<>();
        for (Field f : this.getClass().getDeclaredFields()){
            res.add(f.getName());
        }
        return res;
    }

    public ArrayList<Field> getAllFields(){
        ArrayList<Field> res = new ArrayList<>();
        for (Field f : this.getClass().getDeclaredFields()){
            try {
                f.get((Object) this);
                res.add(f);
            }catch (IllegalAccessException e){
                System.out.println("Field is damaged.");
            }
        }
        return res;
    }

    public ArrayList<Field> getFields(){
        ArrayList<Field> res = new ArrayList<>();
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                if (f.get(this) == null) {
                    res.add(f);
                } else if (f.getLong( this) == 0) {
                    res.add(f);
                }
            } catch (IllegalAccessException e) {
                System.out.println("Field is damaged.");
            } catch (IllegalArgumentException e) {
                //
            }
        }
        return res;
    }

    public String update(){
        name += "1";
        return "Name updated: " + name;
    }
    public boolean checkId(int identificator){
        if (identificator == this.id){
            return true;
        }
        return false;
    }
}
