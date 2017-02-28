package com.haulmont.testtask.DAO.POJO;

/**
 * Created by secret on 21.02.2017.
 */
public class Groups {
    private long id;
    private int number;
    private String faculty;

    public Groups(long id, int number, String faculty) {
        this.id = id;
        this.number = number;
        this.faculty = faculty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

}
