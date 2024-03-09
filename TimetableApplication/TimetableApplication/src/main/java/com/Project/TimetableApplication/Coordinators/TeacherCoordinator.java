package com.Project.TimetableApplication.Coordinators;

import com.Project.TimetableApplication.models.GradeTimetable;
import com.Project.TimetableApplication.models.Period;
import com.Project.TimetableApplication.models.TeacherTimetable;

import java.util.HashMap;

class TeacherCoordinator {
    HashMap<String, TeacherTimetable> teacherTimeTables;
    HashMap<String, GradeTimetable> gradeTimeTables;

    public TeacherCoordinator() {
        teacherTimeTables = new HashMap<>();
        gradeTimeTables = new HashMap<>();
    }

    public void addTeacherTimeTable(String teacherName) {
        teacherTimeTables.put(teacherName, new TeacherTimetable(teacherName));
    }

    public void addGradeTimeTable(String grade) {
        gradeTimeTables.put(grade, new GradeTimetable(grade));
    }

    public void addPeriodToTeacherTimeTable(String teacherName, Period period) {
        teacherTimeTables.get(teacherName).addPeriod(period);
    }

    public void addPeriodToGradeTimeTable(String grade, Period period) {
        gradeTimeTables.get(grade).addPeriod(period);
    }

    public void removePeriodFromTeacherTimeTable(String teacherName, Period period) {
        teacherTimeTables.get(teacherName).removePeriod(period);
    }

    public void removePeriodFromGradeTimeTable(String grade, Period period) {
        gradeTimeTables.get(grade).removePeriod(period);
    }

    public void removeTeacherTimeTable(String teacherName) {
        teacherTimeTables.remove(teacherName);
    }

    public void removeGradeTimeTable(String grade) {
        gradeTimeTables.remove(grade);
    }

    public void printTeacherTimeTable(String teacherName) {
        System.out.println(teacherTimeTables.get(teacherName));
    }

    public void printGradeTimeTable(String grade) {
        System.out.println(gradeTimeTables.get(grade));
    }

    public void printAllTeacherTimeTables() {
        for (String teacherName : teacherTimeTables.keySet()) {
            System.out.println(teacherTimeTables.get(teacherName));
        }
    }

    public void printAllGradeTimeTables() {
        for (String grade : gradeTimeTables.keySet()) {
            System.out.println(gradeTimeTables.get(grade));
        }
    }
}