package com.Project.TimetableApplication.models;

import java.util.ArrayList;
import java.util.List;

public class TeacherTimetable {
    String teacherName;
    List<Period> periods;

    public TeacherTimetable(String teacherName) {
        this.teacherName = teacherName;
        periods = new ArrayList<>();
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public void removePeriod(Period period) {
        periods.remove(period);
    }

    public String getTeacherName() {
        return teacherName;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public String toString() {
       // return "Teacher: " + teacherName + " Grade: " + grade + " Section: " + section + " Periods: " + periods;
        return "Teacher: " + teacherName + " Periods: " + periods;
    }
}
