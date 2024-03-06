package in.prabodh.model;

public class Slot {

    private String subject;
    private String teacher;

    public Slot(String subject, String teacher) {
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
}
