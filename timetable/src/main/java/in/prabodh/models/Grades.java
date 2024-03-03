class Grades {
    private int grade;
    private String subject;
    private String teacherName;
    private int id;

    public Grades(int grade, String subject, String teacherName) {
        this.grade = grade;
        this.subject = subject;
        this.teacherName = teacherName;
    }

    public int getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacherName() {
        return teacherName;
    }
}