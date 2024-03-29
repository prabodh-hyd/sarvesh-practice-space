package com.Project.TimetableApplication.Service;

import com.Project.TimetableApplication.Controller.MainController;

import com.Project.TimetableApplication.Coordinators.TeacherManager;
import com.Project.TimetableApplication.models.*;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import javax.security.auth.Subject;
import java.util.*;

public class SimulateService {


    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();
    //To store fetched grade,subjects values from database.... we use hashMap
    private static HashMap<String,HashSet<String>> hashMap = new HashMap<>() ;


    //To store fetched grade,number of sections values from database.... we use hashMap2
    private static HashMap<String,Integer> hashMap2 = new HashMap<>();


    //To store fetched teachername and subjects from database...we use hashmap3
   // private static HashMap<String,String> hashMap3 = new HashMap<>();




    //To store the working days in a week
    private static String[] days = {"","monday","tuesday","wednesday","thursday","friday","saturday"};

    // sections for any class

    private static String[] sections = {"","a","b","c","d","e","f","g","h","i","j","k","l", "m",
            "n","o","p","q","r","s","t","u","v","w","x","y","z"};


    public static List<String> subjects2 = new ArrayList<>();
    public static List<String> subjects3 = new ArrayList<>();

    public static List<GradeSubjects> getAllGradeSubjectsData() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GradeSubjects> criteriaQuery = criteriaBuilder.createQuery(GradeSubjects.class);
        Root<GradeSubjects> root = criteriaQuery.from(GradeSubjects.class);
        criteriaQuery.select(root);
        return em.createQuery(criteriaQuery).getResultList();
    }

    public static void EnteringFetchedDataIntoHashmap(){
        List<GradeSubjects> list  = getAllGradeSubjectsData();
        for(GradeSubjects a : list){
            HashSet<String> subjects = hashMap.get(a.getGrade());
            if(subjects==null){
                subjects = new HashSet<String>();
                subjects.add(a.getSubject());
                hashMap.put(a.getGrade(),subjects);
                continue;
            }
            subjects.add(a.getSubject());
            hashMap.put(a.getGrade(),subjects);

        }
    }




    public static List<GradeNoOfSections> getAllGradeNoOfSectionsData() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GradeNoOfSections> criteriaQuery = criteriaBuilder.createQuery(GradeNoOfSections.class);
        Root<GradeNoOfSections> root = criteriaQuery.from(GradeNoOfSections.class);
        criteriaQuery.select(root);
        return em.createQuery(criteriaQuery).getResultList();
    }

    public static void EnteringFetchedDataIntoHashmap2(){
        List<GradeNoOfSections> list = getAllGradeNoOfSectionsData();
        for(GradeNoOfSections a : list){
            hashMap2.put(a.getGrade(),a.getNoOfSections());
        }
    }



    public static List<Teacher> getAllTeachers() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
        Root<Teacher> root = criteriaQuery.from(Teacher.class);
        criteriaQuery.select(root);
        return em.createQuery(criteriaQuery).getResultList();
    }


/*
   public static void EnteringFetchedDataIntoHashmap3(){
        List<Teacher> list = getAllTeachers();
        for (Teacher t : list){
            hashMap3.put(t.getName(),t.getSubject());
        }
   }

*/
   public static void addFakeDataIntoDataBase(){
        Faker faker = new Faker();
        em.getTransaction().begin();
       for (HashMap.Entry<String, HashSet<String>> entry : hashMap.entrySet()) {
           String key = entry.getKey();
           HashSet<String> values = entry.getValue();
            for(String value : values){
              String fakeName =   faker.name().fullName();
           Grades obj = new Grades();
           obj.setGrade(key);
           obj.setTeacherName(fakeName);
           obj.setSubject(value);
           em.persist(obj);

           Teacher obj2 = new Teacher();
           obj2.setName(fakeName);
           obj2.setSubject(value);
           em.persist(obj2);
            }
       }
       em.getTransaction().commit();
   }



   public static void CreateTimeTableUsingFakerData() {

        EnteringFetchedDataIntoHashmap();
        EnteringFetchedDataIntoHashmap2();
        addFakeDataIntoDataBase();

        // Loop through days
        for (int i = 1; i < 7; i++) {

            // Loop through grades
            for (Map.Entry<String, HashSet<String>> entry : hashMap.entrySet()) {
                String grade = entry.getKey();

                HashSet<String> subjects = entry.getValue();
                subjects2.clear();
                subjects3.clear();
                for (String s : subjects) {
                    subjects2.add(s);
                    subjects3.add(s);
                }
                int numberOfSections = hashMap2.get(grade);

                // Loop through sections
                for (int j = 1; j <= numberOfSections; j++) {

                    // Loop through periods
                    for (int k = 1; k <= 6; k++) {
                        Period periodObj = new Period();
                        periodObj.setDay(days[i]);
                        periodObj.setGrade(grade);
                        periodObj.setSection(sections[j]);

                        // Assign start and end times based on period
                        int startSlot = getStartSlot(k);
                        periodObj.setStart(startSlot);
                        periodObj.setEnd(startSlot + 1);

                        List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                        if (teachersAvailable.isEmpty()) {

                            subjects2.clear();
                            String newSubject = getOneSubjectFromList();
                          Grades g =  TeacherManager.createNewTeacher(grade, newSubject);
                            periodObj.setTeacher(g.getTeacherName());
                            periodObj.setSubject(g.getSubject());
                        } else {

                            int min = 0;
                            int max = teachersAvailable.size();
                            int randomInt = min + (int)(Math.random() * (max - min));

                            Grades g = teachersAvailable.get(randomInt);
                            Grades g2 = isThisSlotOk(g,days[i],sections[j],startSlot);

                            periodObj.setTeacher(g2.getTeacherName());
                            periodObj.setSubject(g2.getSubject());
                        }

                        MainController obj = new MainController();
                        OutputObject o = obj.RegisterSlot(periodObj);



                    }
                }
            }
        }

    }

       public static String getOneSubjectFromList() {
            if (subjects2.isEmpty()) {
                subjects2.addAll(subjects3);
                Collections.shuffle(subjects2);  // Shuffle the subjects only when the list is empty
            }
            String sub = subjects2.get(0);
            subjects2.remove(sub);
            return sub;
        }



    private static int getStartSlot(int period)  {
        // Define start slots based on period
        return switch (period) {
            case 1 -> 9;
            case 2 -> 10;
            case 3 -> 11;
            case 4 -> 1;
            case 5 -> 2;
            case 6 -> 3;
            case 7 -> 4;
            default -> 0; // Return 0 for invalid periods
        };
    }



        public static void SimulateRealData(){
            EnteringFetchedDataIntoHashmap();
            EnteringFetchedDataIntoHashmap2();


            for (int i = 1; i < 7; i++) {

                // Loop through grades
                for (Map.Entry<String, HashSet<String>> entry : hashMap.entrySet()) {
                    String grade = entry.getKey();

                    HashSet<String> subjects = entry.getValue();
                    subjects2.clear();
                    subjects3.clear();
                    for (String s : subjects) {
                        subjects2.add(s);
                        subjects3.add(s);
                    }
                    int numberOfSections = hashMap2.get(grade);

                    // Loop through sections
                    for (int j = 1; j <= numberOfSections; j++) {

                        // Loop through periods
                        for (int k = 1; k <= 6; k++) {
                            Period periodObj = new Period();
                            periodObj.setDay(days[i]);
                            periodObj.setGrade(grade);
                            periodObj.setSection(sections[j]);

                            // Assign start and end times based on period
                            int startSlot = getStartSlot(k);
                            periodObj.setStart(startSlot);
                            periodObj.setEnd(startSlot + 1);

                            List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                            if (teachersAvailable.isEmpty()) {
                                continue;
                            } else {

                                int min = 0;
                                int max = teachersAvailable.size();
                                int randomInt = min + (int)(Math.random() * (max - min));

                                Grades g = teachersAvailable.get(randomInt);
                                Grades g2 = isThisSlotOk(g,days[i],sections[j],startSlot);

                                periodObj.setTeacher(g2.getTeacherName());
                                periodObj.setSubject(g2.getSubject());
                            }

                            MainController obj = new MainController();
                            OutputObject o = obj.RegisterSlot(periodObj);



                        }
                    }
                }
            }
        }


        public static Grades isThisSlotOk(Grades g,String day,String section,int start){
            System.out.println("Executing is this slot ok");
        String subject = g.getSubject();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Period> root = query.from(Period.class);

            Predicate predicate = cb.and(cb.equal(root.get("subject"), subject),
                                        cb.equal(root.get("grade"),g.getGrade()),
                                        cb.equal(root.get("section"),section),
                                        cb.equal(root.get("day"),day));

            query.select(cb.count(root)).where(predicate);

            Long count = em.createQuery(query).getSingleResult();

            if(count==0){
                return g;
            }
            else{
                String subjectt = searchForZeroCountedSlots(g,day,section);
                if(subjectt.isEmpty()){
                    return g;
                }

                List<String> teachersAvailablee = TeacherManager.getTeachersAvailable(day, start, g.getGrade(), subjectt);
                Grades g2 = new Grades();
                g2.setGrade(g.getGrade());
                g2.setSubject(subjectt);
                g2.setTeacherName(teachersAvailablee.get(0));

                return g2;
            }
        }


        public static String searchForZeroCountedSlots(Grades g,String day,String section){
            System.out.println("Executing search for zero counted slots");
        String s = "";
            List<String> subjects = new ArrayList<>();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Period> query = cb.createQuery(Period.class);
            Root<Period> root = query.from(Period.class);

            Predicate predicate = cb.and(cb.equal(root.get("grade"), g.getGrade()),
                                    cb.equal(root.get("section"),section),
                                    cb.equal(root.get("day"),day));

            query.select(root).where(predicate);

            List<Period> periods =  em.createQuery(query).getResultList();
            for(Period p : periods){
                subjects.add(p.getSubject());
            }
            HashSet<String> totalSubjects = hashMap.get(g.getGrade());
            for(String sub : subjects){
                if(totalSubjects.contains(sub)){
                    continue;
                }
                else{
                    s=sub;
                }
            }
            return s;
        }


}





