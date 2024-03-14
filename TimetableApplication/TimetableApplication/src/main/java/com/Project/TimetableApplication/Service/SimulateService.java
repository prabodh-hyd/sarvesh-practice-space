package com.Project.TimetableApplication.Service;

import com.Project.TimetableApplication.Controller.TestPeriodController;
import com.Project.TimetableApplication.Coordinators.TeacherManager;
import com.Project.TimetableApplication.models.*;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
    private static HashMap<String,String> hashMap3 = new HashMap<>();

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



   public static void EnteringFetchedDataIntoHashmap3(){
        List<Teacher> list = getAllTeachers();
        for (Teacher t : list){
            hashMap3.put(t.getName(),t.getSubject());
        }
   }


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
   }public static void CreateTimeTableUsingFakerData() {
        EnteringFetchedDataIntoHashmap();
        EnteringFetchedDataIntoHashmap2();
        addFakeDataIntoDataBase();
        EnteringFetchedDataIntoHashmap3();

        em.getTransaction().begin(); // Begin transaction outside the loop

        // Loop through days
        for (int i = 1; i < 7; i++) {
            subjects2.clear();
            subjects3.clear();
            // Loop through grades
            for (Map.Entry<String, HashSet<String>> entry : hashMap.entrySet()) {
                String grade = entry.getKey();
                subjects2.clear();
                subjects3.clear();
                HashSet<String> subjects = entry.getValue();
                int numberOfSections = hashMap2.get(grade);

                // Loop through sections
                for (int sectionIndex = 1; sectionIndex <= numberOfSections; sectionIndex++) {
                    subjects2.clear();
                    subjects3.clear();
                    for(String s : subjects) {
                        subjects2.add(s);
                        subjects3.add(s);
                    }
                    // Loop through periods
                    for (int period = 1; period <= 7; period++) {

                        Period periodObj = new Period();
                        periodObj.setDay(days[i]);
                        periodObj.setGrade(grade);
                        periodObj.setSection(sections[sectionIndex]);

                        // Assign start and end times based on period
                        int startSlot = getStartSlot(period);
                        periodObj.setStart(startSlot);
                        periodObj.setEnd(startSlot + 1);

                        // Get available teacher for each subject and assign it to the period
                       // for (String sub : subjects) {
                          //List<String> list = new ArrayList<>(subjects);


                        String sub = getOneSubjectFromList();
                            List<String> teachersAvailable = TeacherManager.getTeachersAvailable(days[i], startSlot, grade, sub);
                            if (!teachersAvailable.isEmpty()) {
                                String teacherName = teachersAvailable.get(0);
                                periodObj.setTeacher(teacherName);
                                periodObj.setSubject(sub);
                                OutputObject o = TestPeriodController.RegisterSlot(periodObj);
                                if(o.getStatus().equalsIgnoreCase("Success") && o.getSchedule().equalsIgnoreCase("Available")){
                                    em.persist(periodObj); // Persist period object
                                }

                            } else {
                                TeacherManager.createNewTeacher(grade, sub);
                                teachersAvailable = TeacherManager.getTeachersAvailable(days[i], periodObj.getStart(), grade, sub);
                                periodObj.setTeacher(teachersAvailable.get(0));
                                periodObj.setSubject(sub);
                                OutputObject o = TestPeriodController.RegisterSlot(periodObj);
                                if(o.getStatus().equalsIgnoreCase("Success") && o.getSchedule().equalsIgnoreCase("Available")){
                                    em.persist(periodObj); // Persist period object
                                }

                            }

                    }
                }
            }
        }

        em.getTransaction().commit(); // Commit transaction outside the loop
    }

        public static String getOneSubjectFromList(){
          //  List<String> subjects3 = new ArrayList<>(subjects2);
            //subjects3 = subjects2;
            if(subjects2.isEmpty()){
                subjects2 .addAll( subjects3);
                String sub = subjects2.get(0);
                subjects2.remove(sub);
                return sub;
            }
                String sub = subjects2.get(0);
                subjects2.remove(sub);
                return sub;
        }
    private static int getStartSlot(int period) {
        // Define start slots based on period
        switch (period) {
            case 1:
                return 9;
            case 2:
                return 10;
            case 3:
                return 11;
            case 4:
                return 1; // Lunch break
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            default:
                return 0; // Return 0 for invalid periods
        }
    }



}





