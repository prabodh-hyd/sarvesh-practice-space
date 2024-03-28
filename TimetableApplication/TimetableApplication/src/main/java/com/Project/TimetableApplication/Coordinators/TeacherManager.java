package com.Project.TimetableApplication.Coordinators;

import com.Project.TimetableApplication.models.Grades;
import com.Project.TimetableApplication.models.Period;
import com.Project.TimetableApplication.models.Teacher;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class TeacherManager {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();

    public static List<String> getTeachersAvailable(String day, int StartTime, String grade, String subject) {
        List<String> teachersAllotted = new ArrayList<String>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Period> criteriaQuery = cb.createQuery(Period.class);
        Root<Period> root = criteriaQuery.from(Period.class);
        Predicate pr = cb.and(
                cb.equal(root.get("start"), StartTime),
                cb.equal(root.get("subject"), subject),
                cb.equal(root.get("day"), day)
        );

        criteriaQuery.select(root).where(pr);
        List<Period> periodsList = em.createQuery(criteriaQuery).getResultList();
        for (Period p : periodsList) {
            teachersAllotted.add(p.getTeacher());
        }

        List<String> result = new ArrayList<String>();
        CriteriaBuilder cb3 = em.getCriteriaBuilder();
        CriteriaQuery<Grades> query3 = cb3.createQuery(Grades.class);
        Root<Grades> root3 = query3.from(Grades.class);
        Predicate pr3 = cb3.and(
                cb3.equal(root3.get("grade"), grade),
                cb3.equal(root3.get("subject"), subject)
        );
        query3.select(root3).where(pr3);
        List<Grades> grades = em.createQuery(query3).getResultList();
        for (Grades g : grades) {
            if (!teachersAllotted.contains(g.getTeacherName())) {
                // Check if the teacher is available at the specified start time
                if (isTeacherAvailable(day,g.getTeacherName(), StartTime)) {
                    result.add(g.getTeacherName());
                }
            }
        }
        return result;
    }



    public static List<Grades> getAllTeachersAvailable(String day, int StartTime,String grade) {
        List<Grades> result = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Grades> criteriaQuery = cb.createQuery(Grades.class);
        Root<Grades> root = criteriaQuery.from(Grades.class);
        Predicate pr = cb.equal(root.get("grade"),grade);
        criteriaQuery.select(root).where(pr);
        List<Grades> GradesList = em.createQuery(criteriaQuery).getResultList();


        CriteriaBuilder cb3 = em.getCriteriaBuilder();
        CriteriaQuery<Period> query3 = cb3.createQuery(Period.class);
        Root<Period> root3 = query3.from(Period.class);
        Predicate pr3 = cb3.and(cb3.equal(root3.get("start"),StartTime),
                cb3.equal(root3.get("grade"), grade),
                cb3.equal(root3.get("day"), day));

        query3.select(root3).where(pr3);
        List<Period> periods =em.createQuery(query3).getResultList();
        List<String> teachersAlloted = new ArrayList<>();
        for(Period p : periods){
            teachersAlloted.add(p.getTeacher());
        }
        if(teachersAlloted.isEmpty()){
            return GradesList;
        }
        for (Grades g : GradesList) {
            boolean assigned = false;
            for (String s : teachersAlloted) {
                if (g.getTeacherName().equalsIgnoreCase(s)) {
                    assigned = true;
                    break;
                }
            }
            if (!assigned) {
                result.add(g);
            }
        }

        return result;
    }


    public static boolean isTeacherAvailable(String day, String teacher, int time) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Period> root = criteriaQuery.from(Period.class);

        Predicate pr = cb.and(cb.equal(root.get("start"),time),
                cb.equal(root.get("teacher"),teacher),
                cb.equal(root.get("day"),day));

        criteriaQuery.select(cb.count(root)).where(pr);

        Long count = em.createQuery(criteriaQuery).getSingleResult();
        if(count>0){
            return false;
        }else{
            return true;
        }

    }

    public static Grades createNewTeacher(String grade, String subject){
        Faker f = new Faker();
        String name =f.name().fullName();

        Grades obj = new Grades();
        obj.setTeacherName(name);
        obj.setGrade(grade);
        obj.setSubject(subject);

        Teacher obj2 = new Teacher();
        obj2.setName(name);
        obj2.setSubject(subject);

        em.getTransaction().begin();
        em.persist(obj);
        em.persist(obj2);
        em.getTransaction().commit();
        return obj;
    }


}
