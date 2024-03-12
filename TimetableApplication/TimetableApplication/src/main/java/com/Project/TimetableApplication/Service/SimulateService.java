package com.Project.TimetableApplication.Service;

import java.util.HashMap;
import java.util.List;

public class SimulateService {
    private List<String>classes;
    private List<String>subjects;
    HashMap<String,Integer> gradeNoOfSections = new HashMap<String,Integer>();
    public SimulateService(List<String> classes, List<String>subjects){
        this.classes=classes;
        this.subjects=subjects;
    }

    public static void simulateData(){

    }
}
