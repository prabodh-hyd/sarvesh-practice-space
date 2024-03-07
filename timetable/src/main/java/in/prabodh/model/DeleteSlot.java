package in.prabodh.model;

public class DeleteSlot {
    private String day;
    private int period;

    public DeleteSlot(String dat, int time) {
        this.day = dat;
        this.period = time;
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
}
