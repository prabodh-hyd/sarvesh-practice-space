package in.prabodh.Service;

import in.prabodh.model.GTSmodel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.LinkedList;
import java.util.List;

public class GetTeacherSecService {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    private String grade;
    private String section;

    public GetTeacherSecService(String grade, String section) {
        this.grade = grade;
        this.section = section;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


    public List<String> getTeachersFromDatabase(){
        List<String> teachers = new LinkedList<>();
        TypedQuery<GTSmodel> query = em.createQuery("SELECT gts FROM GTSmodel gts", GTSmodel.class);
        List<GTSmodel> resultList = query.getResultList();
        for(GTSmodel m : resultList){
            if(m.getGrade().equalsIgnoreCase(grade) && m.getSection().equalsIgnoreCase(section)){
                teachers.add(m.getTeacher());
            }
        }
        return teachers;
    }

}
