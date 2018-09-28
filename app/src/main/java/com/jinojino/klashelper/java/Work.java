package com.jinojino.klashelper.java;

public class Work {
    int id;
    String codeWork;
    String nameCoruse;
    String nameWork;
    String dateStart;
    String dateFinish;
    int submitFlag;
    int alarm;
    int isAlive;
    int workType;

    public Work(int id, String codeWork, String nameCoruse, String nameWork, String dateStart, String dateFinish, int submitFlag, int alarm, int isAlive, int workType) {
        this.id = id;
        this.codeWork = codeWork;
        this.nameCoruse = nameCoruse;
        this.nameWork = nameWork;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.submitFlag = submitFlag;
        this.alarm = alarm;
        this.isAlive = isAlive;
        this.workType = workType;
    }

    public int getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(int isAlive) {
        this.isAlive = isAlive;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public String getNameCoruse() {
        return nameCoruse;
    }

    public void setNameCoruse(String nameCoruse) {
        this.nameCoruse = nameCoruse;
    }

    public String getNameWork() {
        return nameWork;
    }

    public String getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(String dateFinish) {
        this.dateFinish = dateFinish;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeWork() {
        return codeWork;
    }

    public void setCodeWork(String codeWork) {
        this.codeWork = codeWork;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public int getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(int submitFlag) {
        this.submitFlag = submitFlag;
    }

    public int getAlive() {
        return isAlive;
    }

    public void setAlive(int alive) {
        isAlive = alive;
    }
}
