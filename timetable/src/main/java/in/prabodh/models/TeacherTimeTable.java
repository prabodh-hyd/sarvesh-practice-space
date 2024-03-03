class TeacherTimeTable {
    String teacherName;
    List<Period> periods;
    
    public TeacherTimeTable(String teacherName) {
        this.teacherName = teacherName;
        periods = new ArrayList<>();
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public void removePeriod(Period period) {
        periods.remove(period);
    }

    public String getTeacherName() {
        return teacherName;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public String toString() {
        return "Teacher: " + teacherName + " Grade: " + grade + " Section: " + section + " Periods: " + periods;
    }
}