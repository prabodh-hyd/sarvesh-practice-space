package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.models.GradeNoOfSections;
import com.Project.TimetableApplication.models.OutputObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/NoOfSectionsPerClass")
public class GradeNoOfSectionsController {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();

    @PostMapping("/{list}")
    public  List<OutputObject> AssignNoOfSectionsPerClass(@RequestBody List<GradeNoOfSections> list){
        List<OutputObject> outputlist = new ArrayList<OutputObject>();
        for(GradeNoOfSections a : list){
            if(!GradeSecAlreadyExists(a)) {
                em.getTransaction().begin();
                em.persist(a);
                em.getTransaction().commit();
                outputlist.add(new OutputObject("Success","Available"));
            }
            else{
                outputlist.add(new OutputObject("Grade and number of sections are already added","Not Available"));
            }
        }
        return outputlist;


    }
    private  boolean GradeSecAlreadyExists(GradeNoOfSections a){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<GradeNoOfSections> root = query.from(GradeNoOfSections.class);
        Predicate pr= cb.and(cb.equal(root.get("grade"),a.getGrade()),
                cb.equal(root.get("noOfSections"),a.getNoOfSections()));
        query.select(cb.count(root)).where(pr);
        Long count = 0L;
        count = em.createQuery(query).getSingleResult();
        return count > 0;

    }
}
