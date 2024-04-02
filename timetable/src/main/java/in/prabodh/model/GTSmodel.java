package in.prabodh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GTSmodel")
public class GTSmodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long GTSmodelID;
    private String grade;
    private String section;
    private String teacher;


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String subject) {
        this.teacher = subject;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
