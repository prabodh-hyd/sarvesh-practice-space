package com.Project.TimetableApplication.Service;

import com.Project.TimetableApplication.Coordinators.TeacherManager;
import com.Project.TimetableApplication.models.*;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

    private static String[] sections = {"a","b","c","d","e","f","g","h","i","j","k","l", "m",
            "n","o","p","q","r","s","t","u","v","w","x","y","z"};

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
   }
    public static void CreateTimeTableUsingFakerData(){

        EnteringFetchedDataIntoHashmap();
        EnteringFetchedDataIntoHashmap2();
        addFakeDataIntoDataBase();
        EnteringFetchedDataIntoHashmap3();
        for(int i=1;i<7;i++){ //days
             int slotIndex=9;
            int numberOfGrades= hashMap.size();

                for (Map.Entry<String, HashSet<String>> entry : hashMap.entrySet()) {
                    String key = entry.getKey();
                    HashSet<String> subjects = entry.getValue();
                    int numberOfSections = hashMap2.get(key);
                    for(int j=1;j<=numberOfSections;j++){  //no.of sections
                        slotIndex=9;
                        for(String sub : subjects) {
                            em.getTransaction().begin();


                            Period obj = new Period();
                            if(slotIndex==5){
                                slotIndex=9;
                            }

                            if(slotIndex>=9 && slotIndex<12){
                                obj.setStart(slotIndex);
                                obj.setEnd(slotIndex+1);
                                slotIndex++;
                            }
                            else if(slotIndex==12){
                                slotIndex=1;  //lunch break

                            }
                            else if(slotIndex>=1 && slotIndex<5){
                                obj.setStart(slotIndex);
                                obj.setEnd(slotIndex+1);
                                slotIndex++;
                            }

                            obj.setDay(days[i]);
                            obj.setGrade(key);
                            obj.setSection(sections[j]);
                            obj.setSubject(sub);

                          List<String> teachersAvailable =   TeacherManager.getTeachersAvailable(days[i],obj.getStart(),key,sub );
                            if(teachersAvailable.isEmpty()){
                                TeacherManager.createNewTeacher(key,sub);
                                List<String> teachersAvailable2 =   TeacherManager.getTeachersAvailable(days[i],obj.getStart(),key,sub );
                                obj.setTeacher(teachersAvailable2.get(0));
                            }
                            else{
                                obj.setTeacher(teachersAvailable.get(0));
                            }
                            em.persist(obj);
                            em.getTransaction().commit();




                        }
                    }
                }

        }


    }




}
