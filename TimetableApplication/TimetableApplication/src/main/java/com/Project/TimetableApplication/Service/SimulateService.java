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




   public static OutputObject CreateTimeTableUsingFakerData() {
       if(isAlreadyExists()){
           return new OutputObject("Already timetable available....can't create again","Exists");
       }else{

       boolean real = false;

        EnteringFetchedDataIntoHashmap();
        EnteringFetchedDataIntoHashmap2();
        addFakeDataIntoDataBase();

        boolean dataCreated = false;

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
                    if(BreakService.b==0){

                    // Loop through periods
                    for (int k = 1; k <= 7; k++) {
                        Period periodObj = new Period();
                        periodObj.setDay(days[i]);
                        periodObj.setGrade(grade);
                        periodObj.setSection(sections[j]);

                        // Assign start and end times based on period
                        float startSlot = getStartSlot(k);

                        if (startSlot==(float) 12) {
                            periodObj.setStart(startSlot);
                            periodObj.setEnd(1);
                            periodObj.setTeacher("Lunch break");
                            periodObj.setSubject("Lunch break");
                            MainController obj = new MainController();
                            OutputObject o = obj.RegisterSlot(periodObj);
                            System.out.println("Lunch break added");
                            System.out.println(o.toString());

                        }else{

                        periodObj.setStart(startSlot);
                        periodObj.setEnd((int)startSlot + 1);


                        List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                        if (teachersAvailable.isEmpty()) {

                            subjects2.clear();
                            String newSubject = getOneSubjectFromList();
                            Grades g = TeacherManager.createNewTeacher(grade, newSubject);
                            periodObj.setTeacher(g.getTeacherName());
                            periodObj.setSubject(g.getSubject());
                        } else {

                            int min = 0;
                            int max = teachersAvailable.size();
                            int randomInt = min + (int) (Math.random() * (max - min));

                            Grades g = teachersAvailable.get(randomInt);

                            Grades g2 = isThisSlotOk(g, days[i], sections[j], startSlot, real);


                            periodObj.setTeacher(g2.getTeacherName());
                            periodObj.setSubject(g2.getSubject());
                        }

                        MainController obj = new MainController();
                        OutputObject o = obj.RegisterSlot(periodObj);
                            System.out.println(o.toString());

                        dataCreated = true;


                    }

                    }
                  }
                    //if break is allotted
                    else{
                        // Loop through periods
                        for (int k = 1; k <= 7; k++) {
                            Period periodObj = new Period();
                            periodObj.setDay(days[i]);
                            periodObj.setGrade(grade);
                            periodObj.setSection(sections[j]);

                            // Assign start and end times based on period
                            float startSlot = getStartSlot(k);

                            if (startSlot==(float) 12) {
                                periodObj.setStart(startSlot);
                                periodObj.setEnd(1);
                                periodObj.setTeacher("Lunch break");
                                periodObj.setSubject("Lunch break");
                                MainController obj = new MainController();
                                OutputObject o = obj.RegisterSlot(periodObj);
                                System.out.println("Lunch break added");
                                System.out.println(o.toString());

                            } else if (startSlot == 10.15f || startSlot == 11.15f || startSlot == 2.15f || startSlot == 3.15f) {
                                allotBreak(periodObj,startSlot);
                                periodObj.setStart(startSlot);
                                periodObj.setEnd((int)startSlot + 1);


                                List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                                if (teachersAvailable.isEmpty()) {

                                    subjects2.clear();
                                    String newSubject = getOneSubjectFromList();
                                    Grades g = TeacherManager.createNewTeacher(grade, newSubject);
                                    periodObj.setTeacher(g.getTeacherName());
                                    periodObj.setSubject(g.getSubject());
                                } else {

                                    int min = 0;
                                    int max = teachersAvailable.size();
                                    int randomInt = min + (int) (Math.random() * (max - min));

                                    Grades g = teachersAvailable.get(randomInt);

                                    Grades g2 = isThisSlotOk(g, days[i], sections[j], startSlot, real);


                                    periodObj.setTeacher(g2.getTeacherName());
                                    periodObj.setSubject(g2.getSubject());
                                }

                                MainController obj = new MainController();
                                OutputObject o = obj.RegisterSlot(periodObj);
                                System.out.println(o.toString());

                                dataCreated = true;



                            } else{

                                periodObj.setStart(startSlot);
                                periodObj.setEnd((int)startSlot + 1);


                                List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                                if (teachersAvailable.isEmpty()) {

                                    subjects2.clear();
                                    String newSubject = getOneSubjectFromList();
                                    Grades g = TeacherManager.createNewTeacher(grade, newSubject);
                                    periodObj.setTeacher(g.getTeacherName());
                                    periodObj.setSubject(g.getSubject());
                                } else {

                                    int min = 0;
                                    int max = teachersAvailable.size();
                                    int randomInt = min + (int) (Math.random() * (max - min));

                                    Grades g = teachersAvailable.get(randomInt);

                                    Grades g2 = isThisSlotOk(g, days[i], sections[j], startSlot, real);


                                    periodObj.setTeacher(g2.getTeacherName());
                                    periodObj.setSubject(g2.getSubject());
                                }

                                MainController obj = new MainController();
                                OutputObject o = obj.RegisterSlot(periodObj);
                                System.out.println(o.toString());

                                dataCreated = true;


                            }

                        }
                    }
                    
                }
            }
        }


       if(dataCreated){
           return new OutputObject("Timetable Created","Available");
       }else{
           return new OutputObject("Cant create Timetable cause of lack of available data either declaration of grades or declaring subjects and sections.","Not available");
       }}
    }

     /*  public static String getOneSubjectFromList() {
=======
    }

       public static String getOneSubjectFromList() {
>>>>>>> a7fc4a5f421e35dc6c3da455c8976aea9e234338
            if (subjects2.isEmpty()) {
                subjects2.addAll(subjects3);
                Collections.shuffle(subjects2);  // Shuffle the subjects only when the list is empty
            }
            String sub = subjects2.get(0);
            subjects2.remove(sub);
            return sub;
        }
<<<<<<< HEAD
*/
     public static String getOneSubjectFromList() {
         if (subjects2.isEmpty()) {
             subjects2.addAll(subjects3);
             Collections.shuffle(subjects2);
             // Repopulate subjects3 as well
             subjects3 = new ArrayList<>(subjects2);
         }
         String sub = subjects2.get(0);
         subjects2.remove(sub);
         return sub;
     }



    private static float getStartSlot(int period) {
        String startSlot;
        switch (period) {
            case 1:
                startSlot = "9";
                break;
            case 2:
                if (BreakService.b==10){
                    startSlot = "10.15";
                    break;
                }
                startSlot = "10";
                break;
            case 3:
                if (BreakService.b==11){
                    startSlot = "11.15";
                    break;
                }
                startSlot = "11";
                break;
            case 4:
                startSlot = "12";
                break;
            case 5:
                startSlot = "1";
                break;
            case 6:
                if (BreakService.b==2){
                    startSlot = "2.15";
                    break;
                }
                startSlot = "2";
                break;
            case 7:
                if (BreakService.b==3){
                    startSlot = "3.15";
                    break;
                }
                startSlot = "3";
                break;
            default:
                startSlot = "0"; // Return 0 for invalid periods
                break;
        }
        return Float.parseFloat(startSlot);
    }


    public static OutputObject SimulateRealData(){
        if(isAlreadyExists()){
            return new OutputObject("Already timetable available....can't create again","Exists");
        }else{
            List<String> teachers = new ArrayList<String>();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<String> query = cb.createQuery(String.class);
            Root<Grades> root = query.from(Grades.class);

// Selecting distinct teacher names
            query.select(root.get("teacherName")).distinct(true);

            List<String> teacherNames = em.createQuery(query).getResultList();
            teachers.addAll(teacherNames);

             if(teachers.isEmpty()){
                 return new OutputObject("No teachers are available/registered...","Not available");
             }


            boolean real = false;

            EnteringFetchedDataIntoHashmap();
            EnteringFetchedDataIntoHashmap2();
            //addFakeDataIntoDataBase();

            boolean dataCreated = false;

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
                        if(BreakService.b==0){

                            // Loop through periods
                            for (int k = 1; k <= 7; k++) {
                                Period periodObj = new Period();
                                periodObj.setDay(days[i]);
                                periodObj.setGrade(grade);
                                periodObj.setSection(sections[j]);

                                // Assign start and end times based on period
                                float startSlot = getStartSlot(k);

                                if (startSlot==(float) 12) {
                                    periodObj.setStart(startSlot);
                                    periodObj.setEnd(1);
                                    periodObj.setTeacher("Lunch break");
                                    periodObj.setSubject("Lunch break");
                                    MainController obj = new MainController();
                                    OutputObject o = obj.RegisterSlot(periodObj);
                                    System.out.println("Lunch break added");
                                    System.out.println(o.toString());

                                }else{

                                    periodObj.setStart(startSlot);
                                    periodObj.setEnd((int)startSlot + 1);


                                    List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                                    if (teachersAvailable.isEmpty()) {
                                        continue;
                                    } else {

                                        int min = 0;
                                        int max = teachersAvailable.size();
                                        int randomInt = min + (int) (Math.random() * (max - min));

                                        Grades g = teachersAvailable.get(randomInt);

                                        Grades g2 = isThisSlotOk(g, days[i], sections[j], startSlot, real);


                                        periodObj.setTeacher(g2.getTeacherName());
                                        periodObj.setSubject(g2.getSubject());
                                    }

                                    MainController obj = new MainController();
                                    OutputObject o = obj.RegisterSlot(periodObj);
                                    System.out.println(o.toString());

                                    dataCreated = true;


                                }

                            }
                        }
                        //if break is allotted
                        else{
                            // Loop through periods
                            for (int k = 1; k <= 7; k++) {
                                Period periodObj = new Period();
                                periodObj.setDay(days[i]);
                                periodObj.setGrade(grade);
                                periodObj.setSection(sections[j]);

                                // Assign start and end times based on period
                                float startSlot = getStartSlot(k);

                                if (startSlot==(float) 12) {
                                    periodObj.setStart(startSlot);
                                    periodObj.setEnd(1);
                                    periodObj.setTeacher("Lunch break");
                                    periodObj.setSubject("Lunch break");
                                    MainController obj = new MainController();
                                    OutputObject o = obj.RegisterSlot(periodObj);
                                    System.out.println("Lunch break added");
                                    System.out.println(o.toString());

                                } else if (startSlot == 10.15f || startSlot == 11.15f || startSlot == 2.15f || startSlot == 3.15f) {
                                    allotBreak(periodObj,startSlot);
                                    periodObj.setStart(startSlot);
                                    periodObj.setEnd((int)startSlot + 1);


                                    List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                                    if (teachersAvailable.isEmpty()) {
                                        continue;
                                    } else {

                                        int min = 0;
                                        int max = teachersAvailable.size();
                                        int randomInt = min + (int) (Math.random() * (max - min));

                                        Grades g = teachersAvailable.get(randomInt);

                                        Grades g2 = isThisSlotOk(g, days[i], sections[j], startSlot, real);


                                        periodObj.setTeacher(g2.getTeacherName());
                                        periodObj.setSubject(g2.getSubject());
                                    }

                                    MainController obj = new MainController();
                                    OutputObject o = obj.RegisterSlot(periodObj);
                                    System.out.println(o.toString());

                                    dataCreated = true;



                                } else{

                                    periodObj.setStart(startSlot);
                                    periodObj.setEnd((int)startSlot + 1);


                                    List<Grades> teachersAvailable = TeacherManager.getAllTeachersAvailable(days[i], startSlot, grade);

                                    if (teachersAvailable.isEmpty()) {

                                        subjects2.clear();
                                        String newSubject = getOneSubjectFromList();
                                        Grades g = TeacherManager.createNewTeacher(grade, newSubject);
                                        periodObj.setTeacher(g.getTeacherName());
                                        periodObj.setSubject(g.getSubject());
                                    } else {

                                        int min = 0;
                                        int max = teachersAvailable.size();
                                        int randomInt = min + (int) (Math.random() * (max - min));

                                        Grades g = teachersAvailable.get(randomInt);

                                        Grades g2 = isThisSlotOk(g, days[i], sections[j], startSlot, real);


                                        periodObj.setTeacher(g2.getTeacherName());
                                        periodObj.setSubject(g2.getSubject());
                                    }

                                    MainController obj = new MainController();
                                    OutputObject o = obj.RegisterSlot(periodObj);
                                    System.out.println(o.toString());

                                    dataCreated = true;


                                }

                            }
                        }

                    }
                }
            }


            if(dataCreated){
                return new OutputObject("Timetable Created","Available");
            }else{
                return new OutputObject("Cant create Timetable cause of lack of available data either declaration of grades or declaring subjects and sections.","Not available");
            }}

        }


        public static Grades isThisSlotOk(Grades g,String day,String section,float start, boolean b){

            //System.out.println("Executing is this slot ok");
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

               List< String> subjectt = searchForZeroCountedSlots(g,day,section);

                if(subjectt.isEmpty()){
                    return g;
                }
                int min = 0;
                int max = subjectt.size();
                int randomInt = min + (int)(Math.random() * (max - min));
                String su = subjectt.get(randomInt);
                List<Grades> teachersAvailablee = TeacherManager.getTeachersAvailablee(day, start, g.getGrade(), su);
                if(b){
                    if(teachersAvailablee.isEmpty()){
                        return g;
                    }
                    else{
                        int minn = 0;
                        int maxx = teachersAvailablee.size();
                        int randomIn = minn + (int)(Math.random() * (maxx - minn));
                        return teachersAvailablee.get(randomIn);
                    }

                }else {
                    if (teachersAvailablee.isEmpty()) {

                        /*Grades g2 = TeacherManager.createNewTeacher(g.getGrade(), su);
                        g.setTeacherName(g2.getTeacherName());
                        g.setSubject(g2.getSubject());
                        g.setGrade(g2.getGrade());*/
                        return g;
                    } else {
                        int minn = 0;
                        int maxx = teachersAvailablee.size();
                        int randomIn = minn + (int)(Math.random() * (maxx - minn));
                        return teachersAvailablee.get(randomIn);
                    }
                }

            }
        }


    public static List<String> searchForZeroCountedSlots(Grades g, String day, String section) {
        //System.out.println("Executing search for zero counted slots");
        List<String> zeroCountedSubjects = new ArrayList<>();
        List<String> subjects = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Period> query = cb.createQuery(Period.class);
        Root<Period> root = query.from(Period.class);

        Predicate predicate = cb.and(cb.equal(root.get("grade"), g.getGrade()),
                cb.equal(root.get("section"), section),
                cb.equal(root.get("day"), day));

        query.select(root).where(predicate);

        List<Period> periods = em.createQuery(query).getResultList();
        for (Period p : periods) {
            subjects.add(p.getSubject());
        }
        HashSet<String> totalSubjects = hashMap.get(g.getGrade());
        for (String sub : subjects) {
            if (!totalSubjects.contains(sub)) {
                zeroCountedSubjects.add(sub);
            }
        }
        return zeroCountedSubjects;
    }



    public static boolean isAlreadyExists() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Period> root = criteriaQuery.from(Period.class);

        criteriaQuery.select(criteriaBuilder.count(root));
        Long count = em.createQuery(criteriaQuery).getSingleResult();

        EnteringFetchedDataIntoHashmap2();

        int totalNoOfSlots = 0;
        int numberOfGrades = hashMap2.size();

        // Calculating total number of slots
        for (Map.Entry<String, Integer> entry : hashMap2.entrySet()) {
            totalNoOfSlots += entry.getValue();
        }
        totalNoOfSlots *= numberOfGrades  * 6;

        return count >= totalNoOfSlots;
    }


    public static boolean isAlreadyExists(String g) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Period> root = query.from(Period.class);

        // Counting the number of records for the specified grade
        query.select(cb.count(root)).where(cb.equal(root.get("grade"), g));

        Long count = em.createQuery(query).getSingleResult();

        // Getting the total number of slots based on the number of sections for the grade
        int numOFSections = hashMap2.get(g);
        int totalSlots = numOFSections * 6 * 6;

        // Checking if the number of records exceeds the total slots
        return count >= totalSlots;
    }


    public static int b = 0;
    public static OutputObject breakApply(int a){
        if(isAlreadyExists()){
            return new OutputObject("Already timetable available....can't set a break","Already Exists");
        }
        if(a==9){
            return new OutputObject("Classes starts from 9 ... cant give a break at starting of the day","Already exists");
        } else if (a==12) {
            return new OutputObject("Already in a lunch break","Already exists");
        } else if (a==1) {
            return new OutputObject("Just completed lunch break","cant give break");
        } else if(a==10 || a==11 || a==2 || a==3){
            b=a;
            return new OutputObject("Set break successfully","Create timetable to create schedule");

        }
        else{
            return new OutputObject("Not a working hour","Not available");
        }
    }


    public static void allotBreak(Period periodObj,float startSlot){
        periodObj.setStart(startSlot-0.15f);
        periodObj.setEnd(startSlot);
        periodObj.setTeacher("break");
        periodObj.setSubject("break");
        MainController obj = new MainController();
        OutputObject o = obj.RegisterSlot(periodObj);
        System.out.println("break added");
        System.out.println(o.toString());
    }



}





