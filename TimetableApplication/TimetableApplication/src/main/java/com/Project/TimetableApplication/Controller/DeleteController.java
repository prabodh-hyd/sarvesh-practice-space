package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.models.DeleteParticularPeriodObject;
import com.Project.TimetableApplication.models.OutputObject;
import com.Project.TimetableApplication.Service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Deletetimetable")
public class DeleteController {

    @Autowired
    private DeleteService deleteService;

    @DeleteMapping("/deletePeriod")
    public List<OutputObject> deletePeriod(@RequestBody List<DeleteParticularPeriodObject> periods) {
        return deleteService.deletePeriod(periods);
    }

    @DeleteMapping("/deleteGradeTimetable/{grade}")
    public OutputObject deleteGradeTimetable(@PathVariable String grade) {
        return deleteService.deleteGradeTimetable(grade);
    }

    @DeleteMapping("/deleteDayTimetable/{day}")
    public OutputObject deleteDayTimetable(@PathVariable String day){
        return deleteService.deleteDayTimetable(day);
    }

    @DeleteMapping("/deleteParticularSectionofGrade/{grade}/{section}")
    public OutputObject deleteParticularSectionofGrade(@PathVariable String grade,@PathVariable String section){
        return deleteService.deleteParticularSectionofGradeTimetable(grade, section);
    }

    @DeleteMapping("/deleteTeacher/{teacher}/{subject}")
    public OutputObject deleteTeacher(@PathVariable String teacher,@PathVariable String subject){
        return deleteService.deleteTeacher(teacher, subject);
    }

    @DeleteMapping("/deleteWholeTimeTable")
    public OutputObject deleteWholeTimetable(){
        return deleteService.deleteWholeTimetable();
    }
}

