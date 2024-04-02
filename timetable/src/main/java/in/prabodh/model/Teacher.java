package in.prabodh.model;
public class Teacher {
    private String name;
    private int id;
    private String subject;

    public Teacher(String name, int id, String subject) {
        this.name = name;
        this.id=id;
        this.subject=subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

