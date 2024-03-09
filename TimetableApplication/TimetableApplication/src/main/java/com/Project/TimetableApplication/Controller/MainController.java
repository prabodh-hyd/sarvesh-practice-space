package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.models.Grades;
import com.Project.TimetableApplication.models.OutputObject;
import com.Project.TimetableApplication.models.Period;
import com.Project.TimetableApplication.models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/timetable")
public class MainController {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    @PostMapping("/registerByPeriod")
    public OutputObject RegisterSlot(@RequestBody Period p) {
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
            if(numberOfPeriods>=6){
                return new OutputObject("Teacher classes limit has reached","Not Available");
            }
            else{

            //checkimg if already slot existed or not
            CriteriaBuilder cb2 = em.getCriteriaBuilder();
            CriteriaQuery<Long> query2 = cb2.createQuery(Long.class);
            Root<Period> root2 = query2.from(Period.class);

            Predicate predicate2 = cb2.and(
                    cb2.equal(root2.get("start"), p.getStart()),
                    cb2.equal(root2.get("end"), p.getEnd()),
                    cb2.equal(root2.get("teacher"), p.getTeacher()),
                    cb2.equal(root2.get("subject"), p.getSubject()),
                    cb2.equal(root2.get("grade"), p.getGrade()),
                    cb2.equal(root2.get("section"), p.getSection()),
                    cb2.equal(root2.get("day"), p.getDay()));
            query2.select(cb2.count(root2)).where(predicate2);

            Long count = em.createQuery(query2).getSingleResult();
            if (count > 0) {
                //means slot already existed
                return new OutputObject("Failed", "Not available");
            } else {

                em.getTransaction().begin();
                em.persist(p);

                Grades obj = new Grades();
                obj.setGrade(p.getGrade());
                obj.setSubject(p.getSubject());
                obj.setTeacherName(p.getTeacher());
                em.persist(obj);

                Teacher obj2 = new Teacher();
                obj2.setName(p.getTeacher());
                obj2.setSubject(p.getSubject());
                em.persist(obj2);

                em.getTransaction().commit();
                return new OutputObject("success", "Available");
            }}
        }
        else{
            return new OutputObject("Not a working day","Not Available");
        }
    }

    @PostMapping("/registerTeachers")
    public List<OutputObject> registerTeachers(@RequestBody List<Teacher> listOfTeachers) {
List<OutputObject> ll = new LinkedList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Teacher> root = query.from(Teacher.class);
        for (Teacher teacher : listOfTeachers) {

            Predicate predicate = cb.and(

                    cb.equal(root.get("name"), teacher.getName()),
                    cb.equal(root.get("subject"), teacher.getSubject()));


            query.select(cb.count(root)).where(predicate);
            Long count = em.createQuery(query).getSingleResult();
            if (count > 0) {
                ll.add( new OutputObject("Teacher already exists", "Not Available"));

            }else {
                em.getTransaction().begin();
                em.persist(teacher);
                em.getTransaction().commit();
                ll.add(new OutputObject("Success", "Available"));
            }
        }
        return ll;
    }



    @PostMapping("/Simulate")
    public OutputObject simulate(@RequestBody List<Integer> classes,@RequestBody List<String> subjects ){
        List<String> grades = new LinkedList<>();
        for(int c : classes){
            grades.add(String.valueOf(c));
        }


        return new OutputObject("Success","Available");
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

}
