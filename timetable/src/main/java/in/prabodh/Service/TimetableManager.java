package in.prabodh.Service;
import in.prabodh.model.GSmodel;
import in.prabodh.model.GTSmodel;

import in.prabodh.model.Slot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.*;

public class TimetableManager {
    public static Map<String, TreeMap<Integer, Slot>> timetable = new HashMap<>();
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();

    public boolean addSlot(String day, int period, Slot slot) {
        if (day.equalsIgnoreCase("monday") ||
                day.equalsIgnoreCase("tuesday") ||
                day.equalsIgnoreCase("wednesday") ||
                day.equalsIgnoreCase("thursday") ||
                day.equalsIgnoreCase("friday") ||
                day.equalsIgnoreCase("saturday")) {
            // Check if the day already has slots
         /*   if (!timetable.containsKey(day)) {
                timetable.put(day, new TreeMap<>());
            }*/
            if (isRowPresent(day, period, slot.getGrade(), slot.getSection(),slot.getSubject(), slot.getTeacher())) {
                return false;
            }

            // Check for collision
           /* TreeMap<Integer, Slot> dayTimetable = timetable.get(day);
            if (dayTimetable.containsKey(period)) {

                return false;

            } else {*/
                //dayTimetable.put(period, slot);
                em.getTransaction().begin();
                DatabaseConnectionObject obj = new DatabaseConnectionObject();
                obj.setDay(day);
                obj.setPeriod(period);
                obj.setGrade(slot.getGrade());
                obj.setSection(slot.getSection());
                obj.setSubject(slot.getSubject());
                obj.setTeacher(slot.getTeacher());
                em.persist(obj);

                GSmodel obj2 = new GSmodel();
                obj2.setGrade(slot.getGrade());
                obj2.setSubject(slot.getSubject());
                em.persist(obj2);

                GTSmodel obj3 = new GTSmodel();
                obj3.setGrade(slot.getGrade());
                obj3.setTeacher(slot.getTeacher());
                obj3.setSection(slot.getSection());
                em.persist(obj3);

                em.getTransaction().commit();
                return true;
            }
         else {
            return false;
        }
    }


    public boolean isRowPresent(String day, int period,String grade,String section, String subject, String teacher) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<DatabaseConnectionObject> root = query.from(DatabaseConnectionObject.class);

        Predicate predicate = cb.and(
                cb.equal(root.get("day"), day),
                cb.equal(root.get("period"), period),
                cb.equal(root.get("grade"), grade),
                cb.equal(root.get("section"), section)
           /*     cb.equal(root.get("subject"), subject),
                cb.equal(root.get("teacher"), teacher)*/

        );

        query.select(cb.count(root)).where(predicate);

        Long count = em.createQuery(query).getSingleResult();
        return count > 0;
    }

    public boolean deleteRow(String day, int period) {
        List<DatabaseConnectionObject> entities = getAllEntities();
        for (DatabaseConnectionObject entity : entities) {
            if (entity.getDay().equalsIgnoreCase(day) && entity.getPeriod() == period) {
                em.getTransaction().begin();
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            }
        }
        return false;
    }

    public List<DatabaseConnectionObject> getAllEntities() {
        return em.createQuery("SELECT e FROM DatabaseConnectionObject e", DatabaseConnectionObject.class)
                .getResultList();
    }
}
