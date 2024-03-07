package in.prabodh.controllers;


import in.prabodh.Service.GetTeacherSecService;
import in.prabodh.Service.GetTeacherService;
import in.prabodh.Service.SubjectsRegisterService;
import in.prabodh.Service.TimetableManager;
import in.prabodh.model.AllinputObject;
import in.prabodh.model.DeleteSlot;
import in.prabodh.model.OutputObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/timetable")
public class Controller1 {
    @PostMapping("/SubjectsRegister")
    public OutputObject E(@RequestBody AllinputObject obj){
        if(obj.getPeriod()>0 && obj.getPeriod()<9) {
            if(obj.getPeriod()==4){
                return new OutputObject("Lunch break","Not Available");
            }

            SubjectsRegisterService ob = new SubjectsRegisterService(obj);
            return ob.add();
        }
        else{
            return new OutputObject("Not a working hour","Not available");
        }


    }


    @GetMapping("/teachers/{grade}")
    public List<String> getTeachers(@PathVariable String grade){
        GetTeacherService obj = new GetTeacherService(grade);
        return obj.getTeachersFromDatabase();
    }


    @GetMapping("/teachers/{grade}/{section}")
    public List<String> getTeachers(@PathVariable String grade,@PathVariable String section){
        GetTeacherSecService obj = new GetTeacherSecService(grade,section);
        return obj.getTeachersFromDatabase();
    }



    @DeleteMapping("/DeleteSlot")
    public OutputObject deleteRow(DeleteSlot object){
        TimetableManager obj = new TimetableManager();
         if(obj.deleteRow(object.getDay(), object.getPeriod())){
             return new OutputObject("Success","Deleted");
         }
         else{
             return new OutputObject("Failed","Slot not found");
         }
    }


}