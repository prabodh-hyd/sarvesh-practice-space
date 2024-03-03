package in.prabodh.controllers;


import in.prabodh.models.ModelClass;
import in.prabodh.models.OutputObject;
import in.prabodh.models.Teacher;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/OutputObject")
public class OutputController {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
     EntityManager em = emf.createEntityManager();

    public static HashMap<String, LinkedList<String>[]> timetableMap = new HashMap<>();
    int a=1;

    HashMap<String,HashMap<String, LinkedList<String>[]>> hm = new HashMap<String, HashMap<String, LinkedList<String>[]>>();

    @PostMapping("/register")
    public OutputObject registerTimetableEntry(@RequestBody ModelClass m) {
        if (hm.isEmpty()) {
            initializeTimetable();
        }

        HashMap<String, LinkedList<String>[]> ttm = hm.get(m.getDay());
        if (ttm == null) {
            return new OutputObject("Not a working day", "Not available");
        }
        // Add the timetable entry
        String result = addTimetableEntry(ttm, m.getDay(), m.getTeacher(),m.getSubject(), m.getGrade(), m.getSection(), m.getPeriod());

        // Prepare and return the output object
        if (result.equalsIgnoreCase("success")) {
            return new OutputObject(result, "available");
        } else {
            return new OutputObject("failed", "Not available");
        }
    }




    // Helper method to initialize the timetable HashMap
    private void initializeTimetable() {
        hm.put("monday", new HashMap<String, LinkedList<String>[]>());
        hm.put("tuesday", new HashMap<String, LinkedList<String>[]>());
        hm.put("wednesday", new HashMap<String, LinkedList<String>[]>());
        hm.put("thursday", new HashMap<String, LinkedList<String>[]>());
        hm.put("friday", new HashMap<String, LinkedList<String>[]>());
        hm.put("saturday", new HashMap<String, LinkedList<String>[]>());
    }




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

/*
    @GetMapping("/DisplayAll")
    public List<ModelClass> getAll(){


        List<ModelClass> result = new LinkedList<ModelClass>();
        int a = 1;
        while(em.find(ModelClass.class,a)!=null){
            em.getTransaction().begin();
            ModelClass mod = em.find(ModelClass.class,a);
            em.getTransaction().commit();
            result.add(mod);
            a++;
        }
        return result;

    }

*/
/*
ModelClass m = em.find(ModelClass.class,a);

        for (Map.Entry<String, LinkedList<String>[]> entry : timetableMap.entrySet()) {
            String key = entry.getKey();

            LinkedList<String>[] linkedListsArray = entry.getValue();
            for(int i=0;i<linkedListsArray[0].size();i++){
               // em.find(ModelClass.class,a);
                result.add(new ModelClass(key,
                        linkedListsArray[0].get(i),
                        linkedListsArray[1].get(i),
                        linkedListsArray[2].get(i)
                ));
            }


        }
*/
    @GetMapping("/Available/{day}/{grade}/{section}/{period}")
    public LinkedList<String> a(@PathVariable String day, @PathVariable String grade, @PathVariable String section,@PathVariable String period){
        day=day.toLowerCase();
        grade = grade.toLowerCase();
        section=section.toLowerCase();
        period=period.toLowerCase();
        LinkedList<String> ll = new LinkedList<>();
        HashMap<String, LinkedList<String>[]> h =hm.get(day);
        for (Map.Entry<String, LinkedList<String>[]> entry : h.entrySet()){
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


/*
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
            mc.add(new ModelClass(day,teacher,ll2[i].get(0),ll2[i].get(1),ll2[i].get(2)));
        }


        return mc;
    }

*/

    public String addTimetableEntry(HashMap<String, LinkedList<String>[]> ttm,String day,String teacher,String subject, String grade, String section, String period) {

         day=day.toLowerCase();
        teacher=teacher.toLowerCase();
        grade = grade.toLowerCase();
        section=section.toLowerCase();
        period=period.toLowerCase();
        if (!ttm.containsKey(teacher)) {
            ttm.put(teacher, new LinkedList[4]);
            ttm.get(teacher)[0] = new LinkedList<String>();
            ttm.get(teacher)[1] = new LinkedList<String>();
            ttm.get(teacher)[2] = new LinkedList<String>();
            ttm.get(teacher)[3] = new LinkedList<String>();

        }

        LinkedList<String>[] teacherTimetable = ttm.get(teacher);

        int numberOfPeriods=teacherTimetable[3].size();
        if (numberOfPeriods == 6) {
            // numberOfPeriods+=1;

            return "Exceeded the maximum number of periods per day (6).";

        }

        if(numberOfPeriods>8){
            return "Classes limit has reached";
        }


        for (Map.Entry<String, LinkedList<String>[]> entry : ttm.entrySet()) {
            if (!entry.getKey().equals(teacher)) {
                LinkedList<String>[] otherTeacherTimetable = entry.getValue();
                for (int i = 0; i < otherTeacherTimetable[1].size(); i++) {
                    if (otherTeacherTimetable[0].get(i).equals(subject) &&
                            otherTeacherTimetable[1].get(i).equals(grade) &&
                            otherTeacherTimetable[2].get(i).equals(section) &&
                            otherTeacherTimetable[3].get(i).equals(period)) {
                        return "Failed";
                    }

                }

            }
        }


        for (int i = 0; i < teacherTimetable[0].size(); i++) {
            if (teacherTimetable[0].get(i).equals(subject) &&
                    teacherTimetable[1].get(i).equals(grade) &&
                    teacherTimetable[2].get(i).equals(section) &&
                    teacherTimetable[3].get(i).equals(period)) {
                return "Failed";
            }
            if (teacherTimetable[3].get(i).equals(period)) {
                return "Failed";
            }


        }
/*
if(getId(day,teacher, grade, section, period)!=0){
    return "Failed";
}
*/
        teacherTimetable[0].add(subject);
        teacherTimetable[1].add(grade);
        teacherTimetable[2].add(section);
        teacherTimetable[3].add(period);

        ttm.put(teacher,teacherTimetable);


        ModelClass newTimetableEntry = new ModelClass(a,day,teacher,subject, grade, section, period);
        a++;
        // lastInt = a;
        em.getTransaction().begin();
        em.persist(newTimetableEntry); // adding the new object into database ORM
        em.getTransaction().commit();
        hm.put(day,ttm);
        return "Success";

    }


    public Integer getId(String day, String teacher, String grade, String section, String period) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<ModelClass> root = cq.from(ModelClass.class);
            Predicate p1 = cb.equal(root.get(day), day);
            Predicate p2 = cb.equal(root.get(teacher), teacher);
            Predicate p3 = cb.equal(root.get(grade), grade);
            Predicate p4 = cb.equal(root.get(section), section);
            Predicate p5 = cb.equal(root.get(period), period);
            cq.where(cb.and(p1, p2, p3, p4, p5));
            cq.select(root.get("a"));
            int r = em.createQuery(cq).getSingleResult();

            return r;
        } catch (NoResultException e) {
            // Return 0 if no result is found
            return 0;
        }
    }

    @GetMapping()
    public LinkedList<ModelClass> s(@PathVariable String grade, @PathVariable String section){

        LinkedList<ModelClass> ll = new LinkedList<ModelClass>();
        return ll;
    }

}