package in.prabodh.models;

public class Teacher {
    private String subject;
    private String name;
    private int id; // This is the primary key

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
