package in.prabodh.Service;

import in.prabodh.model.Slot;
import jakarta.persistence.*;

public class DatabaseConnectionObject {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private final EntityManager em = emf.createEntityManager();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String day;
    private int time;
    private Slot slot;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
