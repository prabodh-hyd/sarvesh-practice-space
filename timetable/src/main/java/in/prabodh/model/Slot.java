package in.prabodh.model;

public class Slot {
    private String grade;
    private String section;
    private String subject;
    private String teacher;

    public Slot(String grade, String section,String subject, String teacher) {
        this.grade=grade;
        this.section=section;
        this.subject = subject;
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
