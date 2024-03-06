package in.prabodh.Service;

import in.prabodh.model.AllinputObject;
import in.prabodh.model.Slot;

import static in.prabodh.Service.TimetableManager.timetable;

public class SubjectsRegisterService {
    private String day;
    private int time;
    private String subject;
    private String teacher;

    public SubjectsRegisterService(AllinputObject obj){
        this.day=obj.getDay();
        this.time=obj.getTime();
        this.subject=obj.getSubject();
        this.teacher=obj.getTeacher();
    }

    public boolean add( AllinputObject obj){
        Slot s = new Slot(obj.getSubject(),obj.getTeacher());
        return false;

    }
}
