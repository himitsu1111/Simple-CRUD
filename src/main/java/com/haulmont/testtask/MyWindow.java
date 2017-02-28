package com.haulmont.testtask;

import com.haulmont.testtask.DAO.GroupsDAO;
import com.haulmont.testtask.DAO.StudentsDAO;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Created by secret on 22.02.2017.
 */
public class MyWindow extends Window {
    public MyWindow(String s, GroupsDAO gd) {
        //window for add new row in groups
        myMetod(s, "200px", "500px", new RedactionLay(gd, this));
    }
    public MyWindow(String s, StudentsDAO sd, GroupsDAO gd) {
        //win for add students
        myMetod(s, "200px", "1200px", new RedactionLay(sd, gd, this));
    }
    public MyWindow(String s, GroupsDAO gd, String num, String fac) {
        //win for delete row from groups
        myMetod(s, "200px", "500px", new RedactionLay(gd, num, this));
    }

    public MyWindow(String s, GroupsDAO gd, StudentsDAO sd, String[] strings) {
        //win for update student
        myMetod(s, "200px", "1200px", new RedactionLay(gd, sd, strings, this));
    }
    public MyWindow(String s, StudentsDAO sd, String[] strings) {
        //delete student
        myMetod(s, "200px", "1200px", new RedactionLay(sd, strings, this));
    }

    public MyWindow(String s, GroupsDAO gd, String[] strings) {
        // update group

        myMetod(s, "200px", "500px", new RedactionLay(gd, strings, this));
    }
    private void myMetod(String caption, String height, String width, RedactionLay redLay) {
        setCaption(caption);
        setContent(redLay);
        center();
        setHeight(height);
        setWidth(width);
        setModal(true);
        UI.getCurrent().addWindow(this);
    }
}
