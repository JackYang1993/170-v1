package com.baizhi.usermanager.entity;



import java.util.Date;

public class Book {

    private Integer id;

    private String name;

    private Date press_date;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPress_date() {
        return press_date;
    }

    public void setPress_date(Date press_date) {
        this.press_date = press_date;
    }

    public Book(Integer id, String name, Date press_date) {
        this.id = id;
        this.name = name;
        this.press_date = press_date;
    }

    public Book() {
    }
}
