package com.Project.TimetableApplication.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Period")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float start;
    @Column(name = "end_time")
    private float end;

    private String teacher;
    private String subject;
    private String grade;
    private String section;
    @Column(name = "`day`")
    private String day;

    /*
    public Period(int start, int end) {
        this.start = start;
        this.end = end;
    }
*/
    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    @Override
    public String toString() {
        return "day"+ day+"Period: " + start + " - " + end + " Teacher: " + teacher + " Subject: " + subject;
    }
}