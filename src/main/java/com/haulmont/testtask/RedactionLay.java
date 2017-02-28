package com.haulmont.testtask;

import com.haulmont.testtask.DAO.GroupsDAO;
import com.haulmont.testtask.DAO.POJO.Groups;
import com.haulmont.testtask.DAO.StudentsDAO;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by secret on 22.02.2017.
 */
public class RedactionLay extends VerticalLayout {
    RegexpValidator groupValidator = new RegexpValidator("^[0-9]{1,4}$", "Wrong input");
    RegexpValidator stringValidator = new RegexpValidator("^[А-ЯЁа-яёA-Za-z]{1,20}$", "Wrong input");
    RegexpValidator dateValidator = new RegexpValidator("(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)",
                                                        "Wrong date(enter YYYY-MM-DD format)!!!");

    public RedactionLay(GroupsDAO gd, MyWindow myWindow) {
        //insert into groups
        GridLayout grid = new GridLayout(2, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField tf1 = new TextField("NUMBER");
        TextField tf2 = new TextField("FACULTY");
        tf1.addValidator(groupValidator);
        tf2.addValidator(stringValidator);
        Button button1 = new Button("OK");
        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (tf1.isValid() && tf2.isValid()) {
                    gd.addGroup(tf1.getValue(), tf2.getValue());
                    tf1.clear();
                    tf2.clear();
                    UI.getCurrent().setContent(new MyGridLayout()); //for refresh main layout with grids
                }
            }
        });
        Button button2 = new Button("Cancel");
        button2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                myWindow.close();
            }
        });
        grid.addComponent(tf1, 0, 0);
        grid.addComponent(tf2, 1, 0);
        grid.addComponent(button1, 0, 1);
        grid.addComponent(button2, 1, 1);

        addComponent(grid);

    }

    public RedactionLay(GroupsDAO gd, String num, MyWindow myWindow) {
        //delete group
        Label label = new Label("You really want to delete " + num +" group?");
        Button button1 = new Button("OK");
        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                gd.delGroup(num);
                UI.getCurrent().setContent(new MyGridLayout());
            }
        });
        Button button2 = new Button("Cancel");
        button2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                myWindow.close();
            }
        });
        addComponent(label);
        addComponent(button1);
        addComponent(button2);

    }

    public RedactionLay(StudentsDAO sd, GroupsDAO gd, MyWindow myWindow) {
        //insert into students
        final GridLayout grid = new GridLayout(6, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField tf1 = new TextField("NAME");
        TextField tf2 = new TextField("LASTNAME");
        TextField tf3 = new TextField("PATRONYMIC");
        TextField tf4 = new TextField("DATE_OF_BIRTH");

        tf1.addValidator(stringValidator);
        tf2.addValidator(stringValidator);
        tf3.addValidator(stringValidator);
        tf4.addValidator(dateValidator);


        ComboBox cb = new ComboBox("GROUP SELECT");
//        cb.addValidator(new NullValidator("Group not cheked", true));

        List<Groups> al = gd.getGroups();

        for (int i = 0; i < al.size(); i++ ) {
            cb.addItem(al.get(i).getNumber());
        }

        Button button1 = new Button("OK");
        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (tf1.isValid() && tf2.isValid() && tf3.isValid()
                        && tf4.isValid() && cb.isValid()) {
                    sd.addStud(tf1.getValue(), tf2.getValue(), tf3.getValue(), Date.valueOf(tf4.getValue()), (Integer) cb.getValue());
                    tf1.clear();
                    tf2.clear();
                    tf3.clear();
                    tf4.clear();
                    cb.clear();
                    UI.getCurrent().setContent(new MyGridLayout()); //for refresh main layout with grids
                }
            }
        });
        Button button2 = new Button("Cancel");
        button2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                myWindow.close();
            }
        });

        grid.addComponent(tf1, 0, 0);
        grid.addComponent(tf2, 1, 0);
        grid.addComponent(tf3, 2, 0);
        grid.addComponent(tf4, 3, 0);
        grid.addComponent(cb, 4, 0);
        grid.addComponent(button1, 0, 1);
        grid.addComponent(button2, 1, 1);

        addComponent(grid);
    }

    public RedactionLay(GroupsDAO gd, StudentsDAO sd, String[] str, MyWindow myWindow) { //str - row for update
        //update students
        final GridLayout grid = new GridLayout(6, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField tf1 = new TextField("NAME");
        TextField tf2 = new TextField("LASTNAME");
        TextField tf3 = new TextField("PATRONYMIC");
        TextField tf4 = new TextField("DATE_OF_BIRTH");

        tf1.addValidator(stringValidator);
        tf2.addValidator(stringValidator);
        tf3.addValidator(stringValidator);
        tf4.addValidator(dateValidator);

        ComboBox cb = new ComboBox("GROUP SELECT");

//        cb.addValidator(new NullValidator("Group not cheked", true));

        tf1.setValue(str[0]);
        tf2.setValue(str[1]);
        tf3.setValue(str[2]);
        tf4.setValue(str[3]);
        cb.setValue(str[4]);
        long selectedRowId = sd.getStudIdForUpd(str[0], str[1], str[2], Date.valueOf(str[3]), Integer.valueOf(str[4]));

        List<Groups> al = gd.getGroups();

        for (int i = 0; i < al.size(); i++ ) {
            cb.addItem(al.get(i).getNumber());
        }

        Button button1 = new Button("OK");
        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (tf1.isValid() && tf2.isValid() && tf3.isValid()
                        && tf4.isValid() && cb.isValid()) {
                    sd.updStud(tf1.getValue(), tf2.getValue(), tf3.getValue(), Date.valueOf(tf4.getValue()), (Integer) cb.getValue(), selectedRowId);
                    tf1.clear();
                    tf2.clear();
                    tf3.clear();
                    tf4.clear();
                    cb.clear();
                    UI.getCurrent().setContent(new MyGridLayout()); //for refresh main layout with grids
                }
            }
        });
        Button button2 = new Button("Cancel");
        button2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                myWindow.close();
            }
        });

        grid.addComponent(tf1, 0, 0);
        grid.addComponent(tf2, 1, 0);
        grid.addComponent(tf3, 2, 0);
        grid.addComponent(tf4, 3, 0);
        grid.addComponent(cb, 4, 0);
        grid.addComponent(button1, 0, 1);
        grid.addComponent(button2, 1, 1);

        addComponent(grid);
    }

    public RedactionLay(StudentsDAO sd, String[] str, MyWindow myWindow) {
        //delete student
        Label label = new Label("You really want to delete " + str[0] + " " + str[1] +" student?");
        Button button1 = new Button("OK");
        long id = sd.getStudIdForUpd(str[0], str[1], str[2], Date.valueOf(str[3]), Integer.valueOf(str[4]));
        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                sd.deleteStud(id);
                UI.getCurrent().setContent(new MyGridLayout());
            }
        });
        Button button2 = new Button("Cancel");
        button2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                myWindow.close();
            }
        });
        addComponent(label);
        addComponent(button1);
        addComponent(button2);
    }

    public RedactionLay(GroupsDAO gd, String[] str, MyWindow myWindow) {
        //update groups
        final GridLayout grid = new GridLayout(2, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField tf1 = new TextField("NUMBER");
        TextField tf2 = new TextField("FACULTY");
        tf1.setValue(str[0]);
        tf2.setValue(str[1]);

        tf1.addValidator(groupValidator);
        tf2.addValidator(stringValidator);

        long id = gd.getIdGroup(Integer.valueOf(str[0]), str[1]);
        Button button1 = new Button("OK");
        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (tf1.isValid() && tf2.isValid()) {
                    gd.updateGroup(id, Integer.valueOf(tf1.getValue()), tf2.getValue());
                    tf1.clear();
                    tf2.clear();
                    UI.getCurrent().setContent(new MyGridLayout()); //for refresh main layout with grids
                }
            }
        });
        Button button2 = new Button("Cancel");
        button2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                myWindow.close();
            }
        });
        grid.addComponent(tf1, 0, 0);
        grid.addComponent(tf2, 1, 0);
        grid.addComponent(button1, 0, 1);
        grid.addComponent(button2, 1, 1);

        addComponent(grid);
    }
}
