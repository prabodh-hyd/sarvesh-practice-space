package com.Project.TimetableApplication.models;

public class OutputObject {
    private String status;
    private String schedule;

    public OutputObject(String status, String schedule) {
        this.status = status;
        this.schedule = schedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "OutputObject{" +
                "status='" + status + '\'' +
                ", schedule='" + schedule + '\'' +
                '}';
    }
}
