package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/timetable")
public class MainController {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    @PostMapping("/registerByPeriod")
    public OutputObject RegisterSlot(@RequestBody Period p) {

    if((p.getStart()==9 && p.getEnd()==10) ||
                (p.getStart()==10 && p.getEnd()==11) ||
                (p.getStart()==11 && p.getEnd()==12) ||
                (p.getStart()==12 && p.getEnd()==1) ||
                (p.getStart()==1 && p.getEnd()==2) ||
                (p.getStart()==2 && p.getEnd()==3) ||
            (p.getStart()==3 && p.getEnd()==4)) {


        p.setGrade(p.getGrade().toLowerCase());
        p.setSubject(p.getSubject().toLowerCase());
        p.setSection(p.getSection().toLowerCase());
        p.setTeacher(p.getTeacher().toLowerCase());
        if (p.getDay().equalsIgnoreCase("monday") ||
                p.getDay().equalsIgnoreCase("tuesday") ||
                p.getDay().equalsIgnoreCase("wednesday") ||
                p.getDay().equalsIgnoreCase("thursday") ||
                p.getDay().equalsIgnoreCase("friday") ||
                p.getDay().equalsIgnoreCase("saturday")) {


            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Period> root = query.from(Period.class);

            Predicate predicate = cb.and(

                    cb.equal(root.get("teacher"), p.getTeacher()),
                    cb.equal(root.get("subject"), p.getSubject()),
                    cb.equal(root.get("day"), p.getDay()));
            query.select(cb.count(root)).where(predicate);

            Long numberOfPeriods = em.createQuery(query).getSingleResult();
            if (numberOfPeriods > 6 && !p.getTeacher().equalsIgnoreCase("Lunch break")) {
                return new OutputObject("Teacher classes limit has reached", "Not Available");
            } else {

                //checkimg if already slot existed or not
                CriteriaBuilder cb2 = em.getCriteriaBuilder();
                CriteriaQuery<Long> query2 = cb2.createQuery(Long.class);
                Root<Period> root2 = query2.from(Period.class);

                Predicate predicate2 = cb2.and(
                        cb2.equal(root2.get("start"), p.getStart()),
                        cb2.equal(root2.get("grade"), p.getGrade()),
                        cb2.equal(root2.get("section"), p.getSection()),
                        cb2.equal(root2.get("day"), p.getDay()));
                query2.select(cb2.count(root2)).where(predicate2);

                Long count = em.createQuery(query2).getSingleResult();
                if (count > 0) {
                    //means slot already existed
                    return new OutputObject("This slot is not empty", "Not available");
                } else {

                    //checkimg if already teacher is in another period or not
                    CriteriaBuilder cb3 = em.getCriteriaBuilder();
                    CriteriaQuery<Long> query3 = cb3.createQuery(Long.class);
                    Root<Period> root3 = query3.from(Period.class);

                    Predicate predicate3 = cb3.and(
                            cb3.equal(root3.get("start"), p.getStart()),
                            cb3.equal(root3.get("teacher"), p.getTeacher()),
                            cb3.equal(root3.get("day"), p.getDay()));
                    query3.select(cb3.count(root3)).where(predicate3);

                    Long countu = em.createQuery(query3).getSingleResult();
                    if(countu>0 && !p.getTeacher().equalsIgnoreCase("Lunch break")){
                        return new OutputObject("Teacher was in another class at that time","Not Available");
                    }


                   if( !isGradeSubTeaAlreadyExists(p.getGrade(),p.getSubject(),p.getTeacher())) {
                       if(p.getSubject().equalsIgnoreCase("Lunch break") || p.getTeacher().equalsIgnoreCase("Lunch break")){
                           em.getTransaction().begin();
                           em.persist(p);
                           em.getTransaction().commit();
                           return new OutputObject("success", "Available");
                       }
                       return new OutputObject("Teacher was not registered yet","Not Available");
                   }
                    em.getTransaction().begin();
                    em.persist(p);
                   /* Grades obj = new Grades();
                    obj.setGrade(p.getGrade());
                    obj.setSubject(p.getSubject());
                    obj.setTeacherName(p.getTeacher());
                    em.persist(obj);

                    Teacher obj2 = new Teacher();

                    obj2.setName(p.getTeacher());
                    //obj2.setDay(p.getDay());
                    obj2.setSubject(p.getSubject());
                    em.persist(obj2);
                    */
                    em.getTransaction().commit();
                    return new OutputObject("success", "Available");
                }
            }
        } else {
            return new OutputObject("Not a working day", "Not Available");
        }

    }
   /* else if(p.getEnd()==-1 || p.getStart()==-1){
        return new OutputObject("Not a working hour","Not Available");
    }else if (p.getStart() == 0 || p.getEnd() == 0) {
        return new OutputObject("Not a working hour", "Not Available");
    }*/

    else{
        return new OutputObject("Not a working hour","Not Available");
    }
    }

    @PostMapping("/registerTeachers")
    public List<OutputObject> registerTeachers(@RequestBody List<InputRegisterTeacherObject> listOfTeachers) {
List<OutputObject> ll = new LinkedList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Teacher> root = query.from(Teacher.class);
        for (InputRegisterTeacherObject teacher : listOfTeachers) {

            Predicate predicate = cb.and(

                    cb.equal(root.get("name"), teacher.getName()),
                    cb.equal(root.get("subject"), teacher.getSubject()));

            query.select(cb.count(root)).where(predicate);
            Long count = em.createQuery(query).getSingleResult();
            if (count > 0) {
                ll.add( new OutputObject("Teacher already exists", "Already Available"));

            }else {
                em.getTransaction().begin();
                Teacher obj = new Teacher();
                obj.setName(teacher.getName());
                obj.setSubject(teacher.getSubject());

                Grades obj2 = new Grades();
                obj2.setGrade(teacher.getGrade());
                obj2.setTeacherName(teacher.getName());
                obj2.setSubject(teacher.getSubject());


                em.persist(obj);
                em.persist(obj2);
                em.getTransaction().commit();
                ll.add(new OutputObject("Success", "Available"));
            }
        }
        return ll;
    }





    @GetMapping("/{grade}")
        public List<Period> getTimetableForGrade(@PathVariable String grade) {


        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Period> query = cb.createQuery(Period.class);
        Root<Period> root = query.from(Period.class);

        Predicate predicate = cb.equal(root.get("grade"), grade);

        query.select(root).where(predicate);

        return em.createQuery(query).getResultList();


    }

    @GetMapping("/{grade}/{section}")
    public List<Period> getTimetableForGrade(@PathVariable String grade,@PathVariable String section) {


        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Period> query = cb.createQuery(Period.class);
        Root<Period> root = query.from(Period.class);

        Predicate predicate = cb.and(cb.equal(root.get("grade"), grade),
                cb.equal(root.get("section"),section));

        query.select(root).where(predicate);

        return em.createQuery(query).getResultList();


    }

    @GetMapping("/GetTeachers/{grade}")
    public List<String> GetTeachers(@PathVariable String grade){
        List<String> teachers = new ArrayList<String>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Grades> query = cb.createQuery(Grades.class);
        Root<Grades> root = query.from(Grades.class);
        Predicate pr = cb.equal(root.get("grade"),grade);
        query.select(root).where(pr);
        List<Grades> t = em.createQuery(query).getResultList();
        for(Grades grad : t){
            teachers.add(grad.getTeacherName());

        }
        return teachers;

    }


    @GetMapping("/getTimetableOfTeacher/{teacher}")
    public static List<Period> getTeacherSlots(@PathVariable String teacher){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Period> query = cb.createQuery(Period.class);
        Root<Period> root = query.from(Period.class);

        Predicate predicate = cb.equal(root.get("teacher"), teacher);

        query.select(root).where(predicate);

        return em.createQuery(query).getResultList();
    }

    @GetMapping("/getTimetableOfAll")
    public static List<Period> getAllPeriods(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Period> cq = cb.createQuery(Period.class);
        Root<Period> root = cq.from(Period.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();

    }



    public static boolean isGradeSubTeaAlreadyExists(String grade, String subject, String teacher) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Grades> root = query.from(Grades.class);

        Predicate predicate = cb.and(
                cb.equal(root.get("grade"), grade),
                cb.equal(root.get("subject"), subject),
                cb.equal(root.get("teacherName"), teacher)
        );
        query.select(cb.count(root)).where(predicate);
        Long count = em.createQuery(query).getSingleResult();

        return count > 0;
    }

    @PostMapping("/setSubPerClass")
    public OutputObject SetNoOfSubjectsPerClass(@RequestBody GradeSubjectsInputModel input ){
        List<String> classes = input.getClasses();
        List<String> subjects = input.getSubjects();
         for(String grade : classes){
            for(String sub : subjects) {
                em.getTransaction().begin();
                GradeSubjects obj = new GradeSubjects();
                obj.setGrade(grade);
                obj.setSubject(sub);
                em.persist(obj);
                em.getTransaction().commit();
            }
        }

        return new OutputObject("Success","Available");
    }


}
