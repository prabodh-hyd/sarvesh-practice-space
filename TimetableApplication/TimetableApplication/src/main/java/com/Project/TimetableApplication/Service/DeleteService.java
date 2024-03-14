package com.Project.TimetableApplication.Service;

import com.Project.TimetableApplication.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeleteService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    @Transactional
    public List<OutputObject> deletePeriod(List<DeleteParticularPeriodObject> periods) {
        List<OutputObject> outputObjects = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        for (DeleteParticularPeriodObject p : periods) {
            OutputObject output;
            if (p != null) {
                CriteriaDelete<Period> deleteQuery = cb.createCriteriaDelete(Period.class);
                Root<Period> root = deleteQuery.from(Period.class);
                Predicate predicate = cb.and(
                        cb.equal(root.get("start"), p.getClassStartTime()),
                        cb.equal(root.get("grade"), p.getGrade()),
                        cb.equal(root.get("section"), p.getSection()),
                        cb.equal(root.get("day"), p.getDay())
                );

                deleteQuery.where(predicate);
                em.getTransaction().begin();
                int deletedCount = em.createQuery(deleteQuery).executeUpdate();
                em.getTransaction().commit();
                output = new OutputObject("Deleted " + deletedCount + " period(s)", "Success");
            } else {
                output = new OutputObject("No such period exists", "Failed");
            }
            outputObjects.add(output);
        }
        return outputObjects;
    }

    @Transactional
    public OutputObject deleteGradeTimetable(String grade) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Period> deleteQuery = cb.createCriteriaDelete(Period.class);
        Root<Period> root = deleteQuery.from(Period.class);
        Predicate pr = cb.equal(root.get("grade"), grade);
        deleteQuery.where(pr);

        CriteriaBuilder cb2 = em.getCriteriaBuilder();
        CriteriaDelete<Grades> deleteQuery2 = cb2.createCriteriaDelete(Grades.class);
        Root<Grades> root2 = deleteQuery2.from(Grades.class);
        Predicate pr2 = cb2.equal(root2.get("grade"), grade);
        deleteQuery2.where(pr2);

        em.getTransaction().begin();

        int deletedCount = em.createQuery(deleteQuery).executeUpdate();
        em.createQuery(deleteQuery2).executeUpdate();


        em.getTransaction().commit();
        if (deletedCount > 0) {
            return new OutputObject("Success", "Available");
        } else {
            return new OutputObject("Failed to delete", "Not Available");
        }
    }


    @Transactional
    public OutputObject deleteDayTimetable(String day){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Period> deleteQuery = cb.createCriteriaDelete(Period.class);
        Root<Period> root = deleteQuery.from(Period.class);
        Predicate pr = cb.equal(root.get("day"), day);
        deleteQuery.where(pr);
        em.getTransaction().begin();
        int deletedCount = em.createQuery(deleteQuery).executeUpdate();
        em.getTransaction().commit();
        if (deletedCount > 0) {
            return new OutputObject("Success", "Available");
        } else {
            return new OutputObject("Failed to delete", "Not Available");
        }
    }


    @Transactional
    public OutputObject deleteParticularSectionofGradeTimetable(String grade,String section){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Period> deleteQuery = cb.createCriteriaDelete(Period.class);
        Root<Period> root = deleteQuery.from(Period.class);
        Predicate pr = cb.and(cb.equal(root.get("grade"), grade),
                cb.equal(root.get("section"),section));
        deleteQuery.where(pr);
        em.getTransaction().begin();
        int deletedCount = em.createQuery(deleteQuery).executeUpdate();
        em.getTransaction().commit();
        if (deletedCount > 0) {
            return new OutputObject("Success", "Available");
        } else {
            return new OutputObject("Failed to delete", "Not Available");
        }
    }

    @Transactional
    public OutputObject deleteTeacher(String teacher,String subject){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Period> deleteQuery = cb.createCriteriaDelete(Period.class);
        Root<Period> root = deleteQuery.from(Period.class);
        Predicate pr = cb.and(cb.equal(root.get("teacher"), teacher),
                cb.equal(root.get("subject"),subject));
        deleteQuery.where(pr);
        em.getTransaction().begin();
        int deletedCount = em.createQuery(deleteQuery).executeUpdate();
        em.getTransaction().commit();

        CriteriaBuilder cb2 = em.getCriteriaBuilder();
        CriteriaDelete<Teacher> deleteQuery2 = cb2.createCriteriaDelete(Teacher.class);
        Root<Teacher> root2 = deleteQuery2.from(Teacher.class);
        Predicate pr2 = cb2.and(cb2.equal(root2.get("name"), teacher),
                cb2.equal(root2.get("subject"),subject));
        deleteQuery2.where(pr2);
        em.getTransaction().begin();
        int deletedCount2 = em.createQuery(deleteQuery2).executeUpdate();
        em.getTransaction().commit();

        CriteriaBuilder cb3 = em.getCriteriaBuilder();
        CriteriaDelete<Grades> deleteQuery3 = cb3.createCriteriaDelete(Grades.class);
        Root<Grades> root3 = deleteQuery3.from(Grades.class);
        Predicate pr3 = cb3.and(cb3.equal(root3.get("subject"), subject),
                cb3.equal(root3.get("teacherName"),teacher));
        deleteQuery3.where(pr3);
        em.getTransaction().begin();
        int deletedCount3 = em.createQuery(deleteQuery3).executeUpdate();
        em.getTransaction().commit();

        if (deletedCount>0 && deletedCount2>0 && deletedCount3>0){
            return new OutputObject("Success","Available");
        }else {
            return new OutputObject("Failed to delete", "Not Available");
        }
    }


    @Transactional
    public OutputObject deleteWholeTimetable(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Period> deleteQuery = cb.createCriteriaDelete(Period.class);
        Root<Period> root = deleteQuery.from(Period.class);
        deleteQuery.where(cb.isNotNull(root));
        em.getTransaction().begin();
        int a = em.createQuery(deleteQuery).executeUpdate();
        em.getTransaction().commit();
        if(a>0){
            return new OutputObject("Deleted "+ a + " slots","All cleared");
        }else{
            return new OutputObject("Data not found to delete","Not Available");
        }
    }

    @Transactional
    public OutputObject deleteWholeData(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Period> deleteQuery = cb.createCriteriaDelete(Period.class);
        Root<Period> root = deleteQuery.from(Period.class);
        deleteQuery.where(cb.isNotNull(root));
        em.getTransaction().begin();
        int a = em.createQuery(deleteQuery).executeUpdate();
        em.getTransaction().commit();


        CriteriaBuilder cb2 = em.getCriteriaBuilder();
        CriteriaDelete<Teacher> deleteQuery2 = cb2.createCriteriaDelete(Teacher.class);
        Root<Teacher> root2 = deleteQuery2.from(Teacher.class);
        deleteQuery2.where(cb2.isNotNull(root2));
        em.getTransaction().begin();
        int b = em.createQuery(deleteQuery2).executeUpdate();
        em.getTransaction().commit();

        CriteriaBuilder cb3 = em.getCriteriaBuilder();
        CriteriaDelete<Grades> deleteQuery3 = cb3.createCriteriaDelete(Grades.class);
        Root<Grades> root3 = deleteQuery3.from(Grades.class);
        deleteQuery3.where(cb3.isNotNull(root3));
        em.getTransaction().begin();
        int c = em.createQuery(deleteQuery3).executeUpdate();
        em.getTransaction().commit();

        CriteriaBuilder cb4 = em.getCriteriaBuilder();
        CriteriaDelete<GradeNoOfSections> deleteQuery4 = cb4.createCriteriaDelete(GradeNoOfSections.class);
        Root<GradeNoOfSections> root4 = deleteQuery4.from(GradeNoOfSections.class);
        deleteQuery4.where(cb4.isNotNull(root4));
        em.getTransaction().begin();
        int d = em.createQuery(deleteQuery4).executeUpdate();
        em.getTransaction().commit();

        CriteriaBuilder cb5 = em.getCriteriaBuilder();
        CriteriaDelete<GradeSubjects> deleteQuery5 = cb5.createCriteriaDelete(GradeSubjects.class);
        Root<GradeSubjects> root5 = deleteQuery5.from(GradeSubjects.class);
        deleteQuery5.where(cb5.isNotNull(root5));
        em.getTransaction().begin();
        int e = em.createQuery(deleteQuery5).executeUpdate();
        em.getTransaction().commit();

        return new OutputObject("Deleted whole data....you can restart from scratch","Whole deleted");

    }

}
