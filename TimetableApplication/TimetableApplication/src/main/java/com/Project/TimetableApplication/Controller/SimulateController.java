package com.Project.TimetableApplication.Controller;

import com.Project.TimetableApplication.Service.SimulateService;
import com.Project.TimetableApplication.models.GradeSubjects;
import com.Project.TimetableApplication.models.OutputObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/simulate")
public class SimulateController {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private static final EntityManager em = emf.createEntityManager();

    @GetMapping("/SimulateFakeData")
    public OutputObject SimulateData() {
        SimulateService.CreateTimeTableUsingFakerData();
        return new OutputObject("Fake data has created","Available");
    }

    @GetMapping("/SimulateRealData")
    public OutputObject SimulateEnteredData() {
        SimulateService.SimulateRealData();
        return new OutputObject("Fake data has created","Available");
    }
}
