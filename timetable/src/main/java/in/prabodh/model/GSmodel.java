package in.prabodh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GSmodel")
public class GSmodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long GSmodelID;
    private String grade;
    private String subject;


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
