package com.jinojino.klashelper.java;

public class Alarm {
    private int id;
    private int alarmId;
    private String workCode;
    private String alarmTime;
    private String alarmTitle;
    private String workCourse;
    private String workTitle;

    public Alarm(int id, int alarmId, String alarmTitle, String workCode, String alarmTime, String workCourse, String workTitle) {
        this.id = id;
        this.alarmId = alarmId;
        this.alarmTitle = alarmTitle;
        this.workCode = workCode;
        this.alarmTime = alarmTime;
        this.workCourse = workCourse;
        this.workTitle = workTitle;
    }

    public Alarm(int alarmId, String alarmTitle, String workCode, String alarmTime, String workCourse, String workTitle) {
        this.alarmId = alarmId;
        this.alarmTitle = alarmTitle;
        this.workCode = workCode;
        this.alarmTime = alarmTime;
        this.workCourse = workCourse;
        this.workTitle = workTitle;
    }

    public String getWorkCourse() {
        return workCourse;
    }

    public void setWorkCourse(String workCourse) {
        this.workCourse = workCourse;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
