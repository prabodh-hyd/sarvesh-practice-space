package com.Project.TimetableApplication.models;

import jakarta.persistence.*;

@Entity
@Table(name = "GradeNoOfSections")
public class GradeNoOfSections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grade;
    private int noOfSections;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getNoOfSections() {
        return noOfSections;
    }

    public void setNoOfSections(int noOfSections) {
        this.noOfSections = noOfSections;
    }


}
