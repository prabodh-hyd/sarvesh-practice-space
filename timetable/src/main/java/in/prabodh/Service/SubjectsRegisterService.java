package in.prabodh.Service;

import in.prabodh.model.AllinputObject;
import in.prabodh.model.OutputObject;
import in.prabodh.model.Slot;

import static in.prabodh.Service.TimetableManager.timetable;

public class SubjectsRegisterService {
    private String day;
    private int period;
    private String grade;
    private String section;
    private String subject;
    private String teacher;

    public SubjectsRegisterService(AllinputObject obj){
        this.day=obj.getDay().toLowerCase();
        this.period=obj.getPeriod();
        this.grade= obj.getGrade().toLowerCase();
        this.section= obj.getSection().toLowerCase();
        this.subject=obj.getSubject().toLowerCase();
        this.teacher=obj.getTeacher().toLowerCase();
    }
public SubjectsRegisterService(){}
    public OutputObject add(){
        Slot s = new Slot(grade,section,subject,teacher);
        TimetableManager o = new TimetableManager();
        if(o.addSlot(day,period,s)){
            return new OutputObject("Success","Available");
        }
        else {
            return new OutputObject("Failed","Not Available");
        }

    }
}
