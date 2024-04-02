package in.prabodh.Service;

import in.prabodh.model.GTSmodel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.LinkedList;
import java.util.List;

public class GetTeacherService {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    private String grade;

    public GetTeacherService(String grade){
        this.grade=grade;
    }

    public  List<String> getTeachersFromDatabase(){
        List<String> teachers = new LinkedList<>();
    TypedQuery<GTSmodel> query = em.createQuery("SELECT gts FROM GTSmodel gts", GTSmodel.class);
    List<GTSmodel> resultList = query.getResultList();
    for(GTSmodel m : resultList){
        if(m.getGrade().equalsIgnoreCase(grade)){
            teachers.add(m.getTeacher());
        }
    }
    return teachers;
    }
}
