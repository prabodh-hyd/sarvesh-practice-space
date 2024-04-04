package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.models.GradeNoOfSections;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/getSectionsOfClass")
public class getSectionsOfAClass {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    private static String[] sections = {"","a","b","c","d","e","f","g","h","i","j","k","l", "m",
            "n","o","p","q","r","s","t","u","v","w","x","y","z"};
    @GetMapping("/{grade}")
    public static List<String> getSectionsOfClass(@PathVariable String grade){
        List<String> result = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GradeNoOfSections> query = cb.createQuery(GradeNoOfSections.class);
        Root<GradeNoOfSections> root = query.from(GradeNoOfSections.class);

        Predicate predicate = cb.equal(root.get("grade"), grade);

        query.select(root).where(predicate);

        List<GradeNoOfSections> l =  em.createQuery(query).getResultList();
        int a=0;
        for(GradeNoOfSections L : l){
             a = L.getNoOfSections();
        }
        for(int i=1;i<=a;i++){
            result.add(sections[i]);
        }
        return result;
    }
}
