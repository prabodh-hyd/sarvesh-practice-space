package in.prabodh.controllers;


import in.prabodh.Service.SubjectsRegisterService;
import in.prabodh.model.AllinputObject;
import in.prabodh.model.OutputObject;
import in.prabodh.model.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/timetable")
public class Controller1 {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private final EntityManager em = emf.createEntityManager();

    @PostMapping("/SubjectsRegister")
    public OutputObject E(@RequestBody AllinputObject obj){
        new SubjectsRegisterService(obj);

        return new OutputObject("Success","Available");

    }}