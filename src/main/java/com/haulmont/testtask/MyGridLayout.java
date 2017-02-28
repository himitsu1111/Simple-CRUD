package com.haulmont.testtask;

import com.haulmont.testtask.DAO.GroupsDAO;
import com.haulmont.testtask.DAO.POJO.Groups;
import com.haulmont.testtask.DAO.POJO.Students;
import com.haulmont.testtask.DAO.StudentsDAO;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by secret on 22.02.2017.
 */
public class MyGridLayout extends HorizontalLayout {
    public MyGridLayout() {
        GridLayout grid = new GridLayout(7, 7);
        grid.setSpacing(true);

        grid.addStyleName("gridexample");

        Label label = new Label("Groups");
        grid.addComponent(label, 0, 0);

        Label label1 = new Label("Students");
        grid.addComponent(label1, 1, 0);

        GroupsDAO sd = new GroupsDAO();
        StudentsDAO gd = new StudentsDAO();

        List<Groups> listGroups = sd.getGroups();
        List<Students> listStudents = gd.getStudents();

        Grid tgrid = new Grid(); //GROUPS
        tgrid.addColumn("NUMBER");
        tgrid.addColumn("FACULTY");

        listGroups.forEach(groups -> tgrid.addRow(String.valueOf(groups.getNumber()), groups.getFaculty()));

        Grid sgrid = new Grid(); //STUDENTS
        sgrid.addColumn("NAME");
        sgrid.addColumn("LASTNAME");
        sgrid.addColumn("PATRONYMIC");
        sgrid.addColumn("DATE_OF_BIRTH");
        sgrid.addColumn("GROUP");
        listStudents.forEach(students -> sgrid.addRow(students.getName(), students.getLastname(),
                                        students.getPatronymic(), students.getDateOfBirth().toString(),
                                        String.valueOf(students.getGroupId())));

        Button button1 = new Button("add");
        Button button2 = new Button("Update");
        Button button3 = new Button("Delete");

        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                new MyWindow("Groups add", sd);
            }
        });
        button2.addClickListener(new Button.ClickListener() {
            //update group
            public void buttonClick(Button.ClickEvent event) {

                Object myRow = tgrid.getSelectedRow();
                myRow = tgrid.getContainerDataSource().getItem(myRow);
                String[] str = myRow.toString().split(" ");
                new MyWindow("Update groups", sd, str);
            }
        });
        button3.addClickListener(new Button.ClickListener() {
            //delete group
            public void buttonClick(Button.ClickEvent event) {
                Object myRow = tgrid.getSelectedRow();
                myRow = tgrid.getContainerDataSource().getItem(myRow);
                String[] str = myRow.toString().split(" ");
                new MyWindow("Delete group", sd, str[0], str[1]);
            }
        });

        Button button4 = new Button("add");
        Button button5 = new Button("Update");
        Button button6 = new Button("Delete");

        button4.addClickListener(new Button.ClickListener() {
            //add new student
            public void buttonClick(Button.ClickEvent event) {
                new MyWindow("Students add", gd, sd);
            }
        });

        button5.addClickListener(new Button.ClickListener() {
            //update student
            public void buttonClick(Button.ClickEvent event) {

                Object myRow = sgrid.getSelectedRow();
                myRow = sgrid.getContainerDataSource().getItem(myRow);
                String[] str = myRow.toString().split(" ");
                
                new MyWindow("Students update", sd, gd, str);
            }
        });

        button6.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Object myRow = sgrid.getSelectedRow();
                myRow = sgrid.getContainerDataSource().getItem(myRow);
                String[] str = myRow.toString().split(" ");

                new MyWindow("Delete student", gd, str);
            }
        });

        grid.addComponent(tgrid, 0, 1);
        sgrid.setWidth("700px");
        grid.addComponent(sgrid, 1, 1, 3, 1);

        grid.addComponent(new HorizontalLayout(button1, button2, button3), 0, 2);

        grid.addComponent(new HorizontalLayout(button4, button5, button6), 1, 2);

        Button button7 = new Button("Filter by Lastname");
        TextField tf1 = new TextField();
//        tf1.addValidator(new DateRangeValidator(new Validator()));
        BeanFieldGroup<Students> bfg = new BeanFieldGroup<Students>(Students.class);
        bfg.bind(tf1, "LASTNAME");
        tf1.addValidator((new RegexpValidator(
            "^[А-ЯЁа-яёA-Za-z]{1,20}$",
            "Wrong input!!!")));

//        tf1.validate();

        tf1.setImmediate(true);
        button7.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {

                if (tf1.isValid()) {
                    String s = tf1.getValue();
                    sgrid.getContainerDataSource().removeAllItems(); //clear grid data

                    for (Students students : listStudents) {
                        if (students.getLastname().equals(s))
                            sgrid.addRow(students.getName(), students.getLastname(),
                                    students.getPatronymic(), students.getDateOfBirth().toString(),
                                    String.valueOf(students.getGroupId()));
                    }
//                    tf1.setValue("valid");
                }
                else {
                    Notification.show("Введите фимилию студента");
                }
            }
        });

        HorizontalLayout hlFindByLastname = new HorizontalLayout(button7, tf1);
        hlFindByLastname.setSpacing(true);
        grid.addComponent(hlFindByLastname, 1, 3);

        Button button8 = new Button("Filter by Group");
        TextField tf2 = new TextField();
        tf2.addValidator(new RegexpValidator(
                "^[0-9]{1,4}$",
                "Wrong input!!!"));
        tf2.setImmediate(true);
        button8.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (tf2.isValid()) {
                    sgrid.getContainerDataSource().removeAllItems(); //clear grid data
                    int s = Integer.valueOf(tf2.getValue());

                    for (Students students : listStudents) {
                        if (students.getGroupId() == s)
                            sgrid.addRow(students.getName(), students.getLastname(),
                                    students.getPatronymic(), students.getDateOfBirth().toString(),
                                    String.valueOf(students.getGroupId()));
                    }

                    grid.removeComponent(sgrid);
                    grid.addComponent(sgrid, 1, 1, 3, 1);
                }
                else
                    Notification.show("Введите номер группы");
            }
        });

        HorizontalLayout hlFindByGroup = new HorizontalLayout(button8, tf2);
        hlFindByGroup.setSpacing(true);
        grid.addComponent(hlFindByGroup, 1, 4);

        grid.setImmediate(true);
        addComponent(grid);
        setImmediate(true);

        setComponentAlignment(grid, Alignment.TOP_LEFT);
    }
}

