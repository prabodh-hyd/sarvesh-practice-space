package com.Project.TimetableApplication.models;

import java.util.List;

public class GradeSubjectsInputModel {
    private List<String> classes;
    private List<String> subjects;

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}
