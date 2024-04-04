package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.models.GradeNoOfSections;
import com.Project.TimetableApplication.models.Period;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/getAllClasses")
public class getGrades {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();

    @GetMapping("/grades")
    public static List<String> getGrades(){
        List<String> result = new ArrayList<>();
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<GradeNoOfSections> cq = cb.createQuery(GradeNoOfSections.class);
    Root<GradeNoOfSections> root = cq.from(GradeNoOfSections.class);
        cq.select(root);
        List<GradeNoOfSections> a =  em.createQuery(cq).getResultList();
        for(GradeNoOfSections g : a){
            result.add(g.getGrade());
        }
        return result;
    }
}
