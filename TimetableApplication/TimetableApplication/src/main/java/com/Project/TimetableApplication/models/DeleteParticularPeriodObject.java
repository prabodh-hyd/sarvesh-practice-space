package com.Project.TimetableApplication.models;

public class DeleteParticularPeriodObject {
    private int ClassStartTime;
    private String grade;
    private String section;
    private String day;

    public int getClassStartTime() {
        return ClassStartTime;
    }

    public void setClassStartTime(int classStartTime) {
        ClassStartTime = classStartTime;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public DeleteParticularPeriodObject(int classStartTime, String grade, String section, String day) {
        ClassStartTime = classStartTime;
        this.grade = grade;
        this.section = section;
        this.day = day;
    }
}
