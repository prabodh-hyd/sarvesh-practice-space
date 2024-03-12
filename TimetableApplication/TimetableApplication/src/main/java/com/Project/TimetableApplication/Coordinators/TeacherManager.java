package com.Project.TimetableApplication.Coordinators;

import com.Project.TimetableApplication.models.Period;
import com.Project.TimetableApplication.models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class TeacherManager {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();

    public static List<String> getTeachersAvailable(String day, int StartTime,String subject) {
        List<String> teachers = new ArrayList<String>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Period> criteriaQuery = cb.createQuery(Period.class);
        Root<Period> root = criteriaQuery.from(Period.class);
Predicate pr = cb.and(cb.equal(root.get("day"),day),
        cb.and(cb.equal(root.get("subject"),subject)),
        cb.notEqual(root.get("start"),StartTime));


        criteriaQuery.select(root).where(pr);

        // checking if any teacher is available
        List<Period> periodsList = em.createQuery(criteriaQuery).getResultList();
        for(Period p : periodsList){
            teachers.add(p.getTeacher());
        }
        return teachers;
    }

    public static boolean isTeacherAvailable(String day, String teacher, int time) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Period> root = criteriaQuery.from(Period.class);

        Predicate pr = cb.and(cb.equal(root.get("day"),day),
                cb.and(cb.equal(root.get("teacher"),teacher)),
                cb.notEqual(root.get("start"),time));

        criteriaQuery.select(cb.count(root)).where(pr);

        Long count = em.createQuery(criteriaQuery).getSingleResult();

        return count > 0;
    }




}
