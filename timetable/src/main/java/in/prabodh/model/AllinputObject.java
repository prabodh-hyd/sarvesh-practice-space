package in.prabodh.model;


public  class AllinputObject {
    private String day;
    private int period;
    private String grade;
    private String section;
    private String subject;
    private String teacher;

    public AllinputObject(String day, int period, String grade, String section, String subject, String teacher) {
        this.day = day;
        this.period = period;
        this.grade = grade;
        this.section = section;
        this.subject = subject;
        this.teacher = teacher;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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
