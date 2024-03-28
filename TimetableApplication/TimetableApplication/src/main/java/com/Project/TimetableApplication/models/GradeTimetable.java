package com.Project.TimetableApplication.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class GradeTimetable {

    private String grade;
    private List<Period> timeTable;

    public GradeTimetable(String grade) {
        this.grade = grade;
        timeTable = new ArrayList<>();
    }

    public String getGrade() {
        return grade;
    }

    public List<Period> getTimeTable() {
        return timeTable;
    }

    public void addPeriod(Period period) {
        timeTable.add(period);
    }

    public void removePeriod(Period period) {
        timeTable.remove(period);
    }

    public String toString() {
        return "Grade: " + grade + " TimeTable: " + timeTable;
    }
}
