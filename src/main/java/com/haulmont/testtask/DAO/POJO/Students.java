package com.haulmont.testtask.DAO.POJO;

import java.sql.Date;

/**
 * Created by secret on 21.02.2017.
 */
public class Students {
    private long id;
    private String name;
    private String lastname;
    private String patronymic;
    private Date dateOfBirth;
    private long groupId;

    public Students(long id, String name, String lastname,
                    String patronymic, Date dateOfBirth, long groupId) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.groupId = groupId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
