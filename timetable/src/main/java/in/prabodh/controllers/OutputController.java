package in.prabodh.controllers;


import in.prabodh.models.ModelClass;
import in.prabodh.models.OutputObject;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/OutputObject")
public class OutputController {


    @PostMapping("/register")
    public OutputObject registerTimetableEntry(@RequestBody ModelClass m) {

        String result = addTimetableEntry(m.getTeacher(), m.getGrade(), m.getSection(), m.getPeriod());
        if (result.equalsIgnoreCase("success")) {
            return new OutputObject(result, "available");
        } else  if (result.equalsIgnoreCase("Failed")) {
            return new OutputObject(result, "Not available");
        }
        else {
            return new OutputObject(result,"In a break");
        }
    }


/*
    @GetMapping("/DisplayAll")
    public ResponseEntity<Map<String, LinkedList<String>[]>> getAll(){
        Map<String, LinkedList<String>[]> resultMap = new HashMap<>();

        // Assuming timetableMap is a HashMap<String, LinkedList<String>[]>
        for (Map.Entry<String, LinkedList<String>[]> entry : timetableMap.entrySet()) {
            String key = entry.getKey();
            LinkedList<String>[] linkedListsArray = entry.getValue();

            // Put the key-value pair into resultMap
            resultMap.put(key, linkedListsArray);
        }
        return ResponseEntity.ok(resultMap);
    }
*/

    @GetMapping("/DisplayAll")
    public List<ModelClass> getAll(){

        List<ModelClass> result = new LinkedList<ModelClass>();


        for (Map.Entry<String, LinkedList<String>[]> entry : timetableMap.entrySet()) {
            String key = entry.getKey();

            LinkedList<String>[] linkedListsArray = entry.getValue();
            for(int i=0;i<linkedListsArray[0].size();i++){
                result.add(new ModelClass(key,
                        linkedListsArray[0].get(i),
                        linkedListsArray[1].get(i),
                        linkedListsArray[2].get(i)
                ));
            }


        }

        return result;
    }

    @GetMapping("/Available/{grade}/{section}/{period}")
    public LinkedList<String> a(@PathVariable String grade, @PathVariable String section,@PathVariable String period){
        grade = grade.toLowerCase();
        section=section.toLowerCase();
        period=period.toLowerCase();
        LinkedList<String> ll = new LinkedList<>();
        for (Map.Entry<String, LinkedList<String>[]> entry : timetableMap.entrySet()){
            String t = entry.getKey();
            LinkedList<String>[] TeachetTimeTable = entry.getValue();
            //!TeachetTimeTable[0].contains(grade) && !TeachetTimeTable[1].contains(section) &&
            if(TeachetTimeTable[0].contains(grade) && TeachetTimeTable[1].contains(section) && TeachetTimeTable[2].contains(period)){
                continue;
            }
            if(TeachetTimeTable[2].size()==6 || TeachetTimeTable[2].size()>8){
                continue;
            }

            if( !TeachetTimeTable[2].contains(period) ){
                ll.add(t);
            }

        }
        return ll;
    }



    @GetMapping("/{teacher}")
    public List<ModelClass> getTeacherInfo(@PathVariable String teacher) {
        //return new ModelClass(teacher, "Grade", "Section", "Period");
        teacher=teacher.toLowerCase();
        LinkedList<String>[] ll = timetableMap.get(teacher);
        List<ModelClass> mc = new ArrayList<ModelClass>();
        if(ll==null){
            return null;
        }
        LinkedList<String> ll20 = new LinkedList<String>();
        for(int i=0;i<ll[0].size();i++){
            ll20.add(ll[0].get(i));
            ll20.add(ll[1].get(i));
            ll20.add(ll[2].get(i));
        }
        int a = ll20.size()/3;
        LinkedList<String>[] ll2 = new LinkedList[a];
        for (int i = 0; i < a; i++) {
            ll2[i] = new LinkedList<>();
            for(int j=i*3;j<i*3+3;j++){

                ll2[i].add(ll20.get(j));

            }}

        for(int i=0;i<ll2.length;i++){
            mc.add(new ModelClass(teacher,ll2[i].get(0),ll2[i].get(1),ll2[i].get(2)));
        }


        return mc;
    }


    public static HashMap<String, LinkedList<String>[]> timetableMap = new HashMap<>();
    public static String addTimetableEntry(String teacher, String grade, String section, String period) {
        // int numberOfPeriods=0;
        teacher=teacher.toLowerCase();
        grade = grade.toLowerCase();
        section=section.toLowerCase();
        period=period.toLowerCase();
        if (!timetableMap.containsKey(teacher)) {
            timetableMap.put(teacher, new LinkedList[3]);
            timetableMap.get(teacher)[0] = new LinkedList<String>();
            timetableMap.get(teacher)[1] = new LinkedList<String>();
            timetableMap.get(teacher)[2] = new LinkedList<String>();
        }

        LinkedList<String>[] teacherTimetable = timetableMap.get(teacher);

        int numberOfPeriods=teacherTimetable[2].size();
        if (numberOfPeriods == 6) {
            // numberOfPeriods+=1;

            return "Exceeded the maximum number of periods per day (6).";

        }

        if(numberOfPeriods>8){
            return "Classes limit has reached";
        }


        for (Map.Entry<String, LinkedList<String>[]> entry : timetableMap.entrySet()) {
            if (!entry.getKey().equals(teacher)) {
                LinkedList<String>[] otherTeacherTimetable = entry.getValue();
                for (int i = 0; i < otherTeacherTimetable[1].size(); i++) {
                    if (otherTeacherTimetable[0].get(i).equals(grade) &&
                            otherTeacherTimetable[1].get(i).equals(section) &&
                            otherTeacherTimetable[2].get(i).equals(period)) {
                        return "Failed";
                    }

                }

            }
        }


        for (int i = 0; i < teacherTimetable[0].size(); i++) {
            if (teacherTimetable[0].get(i).equals(grade) &&
                    teacherTimetable[1].get(i).equals(section) &&
                    teacherTimetable[2].get(i).equals(period)) {
                return "Failed";
            }
            if (teacherTimetable[2].get(i).equals(period)) {
                return "Failed";
            }


        }

        teacherTimetable[0].add(grade);
        teacherTimetable[1].add(section);
        teacherTimetable[2].add(period);

        return "Success";

    }
}