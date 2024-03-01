package in.prabodh.models;



public class ModelClass {

    private String teacher;
    private String grade;
    private String section;
    private String period;

    public ModelClass(){}
    public ModelClass(String teacher, String grade, String section, String period) {
        this.teacher = teacher;
        this.grade = grade;
        this.section = section;
        this.period = period;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}