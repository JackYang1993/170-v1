package com.baizhi.usermanager.entity;

import java.io.Serializable;

public class StatusInfo implements Serializable {

    private Integer id;

    private String status;

    private Integer num;

    private String dateTime;

    public StatusInfo() {
    }

    public StatusInfo(Integer id, String status, Integer num, String dateTime) {
        this.id = id;
        this.status = status;
        this.num = num;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "StatusInfo{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", num=" + num +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
