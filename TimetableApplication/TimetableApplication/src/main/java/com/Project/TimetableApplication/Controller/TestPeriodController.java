package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.models.Grades;
import com.Project.TimetableApplication.models.OutputObject;
import com.Project.TimetableApplication.models.Period;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class TestPeriodController {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    public static OutputObject RegisterSlot( Period p) {

        if((p.getStart()==9 && p.getEnd()==10) ||
                (p.getStart()==10 && p.getEnd()==11) ||
                (p.getStart()==11 && p.getEnd()==12) ||
                (p.getStart()==1 && p.getEnd()==2) ||
                (p.getStart()==2 && p.getEnd()==3) ||
                (p.getStart()==3 && p.getEnd()==4) ||
                (p.getStart()==4 && p.getEnd()==5)) {


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
                if (numberOfPeriods > 6) {
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
                        if(countu>0){
                            return new OutputObject("Teacher was in another class at that time","Not Available");
                        }


                        if( !isGradeSubTeaAlreadyExists(p.getGrade(),p.getSubject(),p.getTeacher())) {
                            return new OutputObject("Teacher was not registered yet","Not Available");
                        }
                        //checkimg if already teacher is in another period or not
                        CriteriaBuilder cb4 = em.getCriteriaBuilder();
                        CriteriaQuery<Long> query4 = cb4.createQuery(Long.class);
                        Root<Period> root4 = query4.from(Period.class);

                        Predicate predicate4 = cb4.and(
                                cb4.equal(root4.get("subject"), p.getSubject()),
                                cb4.equal(root4.get("grade"), p.getGrade()),
                                cb4.equal(root4.get("section"), p.getSection()),
                                cb4.equal(root4.get("day"), p.getDay()));
                        query4.select(cb4.count(root4)).where(predicate4);

                        Long countt = em.createQuery(query4).getSingleResult();

                        if(countt>0){
                            return new OutputObject("Subject was already done","Already exists");
                        }
                   /*     em.getTransaction().begin();
                        em.persist(p);
                    Grades obj = new Grades();
                    obj.setGrade(p.getGrade());
                    obj.setSubject(p.getSubject());
                    obj.setTeacherName(p.getTeacher());
                    em.persist(obj);

                    Teacher obj2 = new Teacher();

                    obj2.setName(p.getTeacher());
                    //obj2.setDay(p.getDay());
                    obj2.setSubject(p.getSubject());
                    em.persist(obj2);

                        em.getTransaction().commit();*/
                        return new OutputObject("success", "Available");
                    }
                }
            } else {
                return new OutputObject("Not a working day", "Not Available");
            }

        }
        else if(p.getEnd()==-1 || p.getStart()==-1){
            return new OutputObject("Not a working hour","Not Available");
        }else if (p.getStart() == 0 || p.getEnd() == 0) {
            return new OutputObject("Not a working hour", "Not Available");
        }

        else{
            return new OutputObject("Not a working hour","Not Available");
        }
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

}
