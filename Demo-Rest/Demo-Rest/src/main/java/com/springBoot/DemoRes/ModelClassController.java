package com.springBoot.DemoRes;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

import static com.springBoot.DemoRes.OutputController.timetableMap;


@RestController
@RequestMapping("/ModelClass")
public class ModelClassController {

    @PutMapping("/Update/{t}")
    public String update(@PathVariable String t,@RequestBody ModelClass obj){

        LinkedList<String>[] ll = timetableMap.get(t);
        if(ll[2].contains(obj.getPeriod().toLowerCase())){
            int a = ll[2].indexOf(obj.getPeriod().toLowerCase());
            ll[0].set(a,obj.getGrade());
            ll[1].set(a,obj.getSection());
            return "Updated Successfully";
        }
        return "Sorry....Cant update!";
    }

    @DeleteMapping("/Delete")
    public String d(@RequestBody ModelClass m){
        String grade=m.getGrade().toLowerCase();
        String section=m.getSection().toLowerCase();
        String period=m.getPeriod().toLowerCase();


        String t = m.getTeacher().toLowerCase();
        if(timetableMap.containsKey(t) && timetableMap.get(t)[0].contains(grade)
        && timetableMap.get(t)[1].contains(section)
        && timetableMap.get(t)[2].contains(period)
        ){
            LinkedList<String>[] ll = timetableMap.get(t);
            int a = ll[0].indexOf(grade);
            int b = ll[1].indexOf(section);
            int c = ll[2].indexOf(period);
            ll[0].remove(a);
            ll[1].remove(b);
            ll[2].remove(c);
            timetableMap.put(t,ll);

            return "Success.....Double Success!";
        }
        return "Error: Your slot was not found in the timetable";
    }



}
