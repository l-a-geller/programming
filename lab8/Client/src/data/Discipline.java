package data;

import java.io.Serializable;

public class Discipline implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Integer lectureHours; //Поле может быть null
    private Integer practiceHours; //Поле не может быть null
    private Long selfStudyHours; //Поле может быть null

    public Discipline(String data){
        this.name = data;
    }
    public Discipline(String name, Integer practiceHours, Integer lectureHours, Long selfStudyHours){
        this.name = name;
        this.practiceHours = practiceHours;
        this.lectureHours = lectureHours;
        this.selfStudyHours = selfStudyHours;
    }
    public String toString(){
        return this.name;
    }
    public Integer getLecture(){return this.lectureHours;}
    public Integer getPractice(){return this.practiceHours;}
    public Long getSelfStudy(){return this.selfStudyHours;}
}