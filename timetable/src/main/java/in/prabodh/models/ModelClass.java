package in.prabodh.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class ModelClass {
@Id
    private int a;

    private String day;
    private String teacher;
    private String grade;
    private String section;
    private String period;

    public ModelClass(){}
    public ModelClass(int a, String day, String teacher, String grade, String section, String period) {
        this.a = a;
        this.day = day;
        this.teacher = teacher;
        this.grade = grade;
        this.section = section;
        this.period = period;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPeriod() {
        return period;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}