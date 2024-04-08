package com.Project.TimetableApplication.Service;

import com.Project.TimetableApplication.models.OutputObject;

public class BreakService {
    public static int b = 0;
    public static OutputObject breakApply(int a){
        if(a==9){
            return new OutputObject("Classes starts from 9 ... cant give a break at starting of the day","Already exists");
        } else if (a==12) {
            return new OutputObject("Already in a lunch break","Already exists");
        } else if (a==1) {
            return new OutputObject("Just completed lunch break","cant give break");
        } else if(a==10 || a==11 || a==2 || a==3){

            b=a;
            return new OutputObject("Alloted the break...create timetable now","Available");
        }
        else{
            return new OutputObject("Not a working hour","Not available");
        }
    }
}
